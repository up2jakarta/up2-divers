package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.*;
import io.github.up2jakarta.csv.core.MapperFactory.FragmentProperty;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.*;
import io.github.up2jakarta.csv.misc.Beans;
import jakarta.validation.Valid;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.misc.Beans.getBean;
import static io.github.up2jakarta.csv.misc.Beans.getFieldType;
import static java.util.Collections.unmodifiableList;

final class BeanSupport<D extends DataType<D>> {

    private final MapperContext<D> context;

    BeanSupport(MapperContext<D> context) {
        this.context = context;
    }

    private static Fragment checkFragment(Field field, Fragment csv) throws BeanException {
        if (csv != null) {
            if (csv.value() < 0) {
                throw new BeanException(field, "@Fragment[value] must be positive");
            }
            if (!Segment.class.isAssignableFrom(field.getType())) {
                throw new BeanException(field, "type must implements Segment");
            }
        }
        return csv;
    }

    private static Position checkProperty(Field field, Position csv) throws BeanException {
        if (csv != null) {
            if (csv.value() < 0) {
                throw new BeanException(field, "@Position[value] must be positive");
            }
            if (field.isAnnotationPresent(Valid.class)) {
                throw new BeanException(field, "must not be annotated with @Valid");
            }
            if (field.getAnnotationsByType(ValidOverride.class).length != 0) {
                throw new BeanException(field, "must not be annotated with @ValidOverride");
            }
            if (field.getAnnotationsByType(PositionOverride.class).length != 0) {
                throw new BeanException(field, "must not be annotated with @PositionOverride");
            }
            if (field.getAnnotationsByType(FragmentOverride.class).length != 0) {
                throw new BeanException(field, "must not be annotated with @FragmentOverride");
            }
        }
        return csv;
    }

    private static void checkDefault(Field field, Conversion<?> conversion) throws BeanException {
        final Up2Default defaultValue = field.getAnnotation(Up2Default.class);
        if (defaultValue != null) {
            try {
                conversion.apply(defaultValue.value());
            } catch (Throwable ex) {
                throw new BeanException(field, "@Up2Default[value] cannot be converted");
            }
        }
    }

    private static Method getRepeatableValue(Class<? extends Annotation> annotationType) throws BeanException {
        final Repeatable repeatable = annotationType.getDeclaredAnnotation(Repeatable.class);
        if (repeatable != null) {
            return Beans.getMethod(repeatable.value(), "value", "class", "value()");
        }
        return null;
    }

    static <A extends Annotation> List<A> getAnnotationsByType(Class<A> annotationType, AnnotatedElement element) throws BeanException {
        final Method repeatValue = getRepeatableValue(annotationType);
        final List<A> result = new LinkedList<>();
        for (final Annotation annotation : element.getAnnotations()) {
            final Class<? extends Annotation> aType = annotation.annotationType();
            // direct
            if (annotationType.equals(aType)) {
                //noinspection unchecked
                result.add((A) annotation);
            }
            // indirect
            if (repeatValue != null && repeatValue.getDeclaringClass().equals(aType)) {
                try {
                    //noinspection unchecked
                    final A[] indirectArray = (A[]) repeatValue.invoke(annotation);
                    result.addAll(Arrays.asList(indirectArray));
                } catch (Throwable t) {
                    throw new BeanException(repeatValue.getDeclaringClass(), t.getMessage());
                }
            }
            // shortcuts
            final A[] shortcutArray = aType.getAnnotationsByType(annotationType);
            result.addAll(Arrays.asList(shortcutArray));
        }
        return result;
    }

    static <D extends DataType<D>> List<ProcessorWrapper<?, D>> getProcessors(BeanContext context, Field field) throws BeanException {
        final List<ProcessorWrapper<?, D>> result = new LinkedList<>();
        for (final Annotation annotation : field.getAnnotations()) {
            final Class<? extends Annotation> aType = annotation.annotationType();
            final Processor processor = aType.getAnnotation(Processor.class);
            if (processor != null) {
                final Class<? extends ConfigurableProcessor<?>> pType = processor.value();
                final Type[] types = Beans.getTypeArguments(pType, ConfigurableProcessor.class);
                if (types.length == 0 || aType != types[0]) {
                    final String aName = aType.getSimpleName();
                    throw new BeanException(aType, "@Processor[value] must implements ConfigurableProcessor<" + aName + ">");
                }
                final ConfigurableProcessor<? extends Annotation> delegate = getBean(context, pType);
                //noinspection rawtypes,unchecked
                result.add(new ProcessorWrapper(delegate, processor.skip(), annotation));
            }
        }
        return unmodifiableList(result);
    }

    private Conversion<?> getConversion(MapperContext<D> context, Field field, Class<?> type) throws BeanException {
        final Up2Converter converter = field.getAnnotation(Up2Converter.class);
        final Error error = field.getAnnotation(Error.class);
        if (converter != null) {
            final TypeConverter<?> tConverter = getBean(context.getContext(), converter.value());
            if (!tConverter.getSupportedType().isAssignableFrom(field.getType())) {
                throw new BeanException(field, "@Converter[value] does not support " + field.getType().getSimpleName());
            }
            if (error == null) {
                return Conversion.of(tConverter::parse, tConverter.getErrorSeverity(), tConverter.getErrorCode());
            }
            return Conversion.of(tConverter::parse, error);
        }
        for (final Annotation annotation : field.getAnnotations()) {
            final Resolver resolver = annotation.annotationType().getAnnotation(Resolver.class);
            if (resolver != null) {
                //noinspection unchecked
                var conversionResolver = (ConversionResolver<Annotation>) getBean(context.getContext(), resolver.value());
                final Conversion<?> conversion = conversionResolver.resolve(annotation, field);
                return Conversion.of(conversion, error);
            }
        }
        return context.getConversion(field, type);
    }

    private List<Property<?, D>> getProperties(Class<? extends Segment> beanType, MapperContext<D> context) throws BeanException {
        if (context.push(beanType)) {
            throw new BeanException(beanType, "cyclic fragment is not allowed");
        }
        final List<Property<?, D>> result = new LinkedList<>();
        final Class<?> superClass = beanType.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            final Type[] arguments = Beans.getTypeArguments(beanType.getGenericSuperclass());
            //noinspection unchecked
            final Class<? extends Segment> superType = (Class<? extends Segment>) superClass;
            context.getChecker().beforeSuperSegment(superType);
            final List<Property<?, D>> superProperties = getProperties(superType, context.with(superType, arguments));
            context.getChecker().afterSuperSegment(superType);
            result.addAll(superProperties);
        }
        final Field[] fields = beanType.getDeclaredFields();
        final int offset = context.getOffset();
        for (final Field field : fields) {
            final Class<?> fieldType = getFieldType(field, context.getArguments());
            final Fragment fragment = checkFragment(field, context.getFragment(field));
            final Position position = checkProperty(field, context.getPosition(field));
            if (fragment != null) {
                final D dataType = context.getDataType(field);
                //noinspection unchecked
                final Class<? extends Segment> fType = (Class<? extends Segment>) fieldType;
                final int fOffset = offset + fragment.value();
                context.getChecker().beforeFragmentProperty(field, fType);
                final List<Property<?, D>> fProps = getProperties(fType, context.with(field, fOffset, fType));
                context.getChecker().afterFragmentProperty(field, fType);
                final ValidationContext fContext = context.getValidation(field);
                result.add(new FragmentProperty<>(fType, dataType, field, fOffset, fContext, fProps));
            } else if (position != null) {
                final D dataType = context.getDataType(field);
                final int index = offset + position.value();
                context.getChecker().beforePositionProperty(field, fieldType, index);
                final List<ProcessorWrapper<?, D>> processors = getProcessors(context.getContext(), field);
                if (CharSequence.class == fieldType || fieldType == String.class) {
                    result.add(new StringProperty<>(field, dataType, index, processors));
                } else {
                    final Conversion<?> conversion = getConversion(context, field, fieldType);
                    checkDefault(field, conversion);
                    result.add(new ConvertedProperty<>(field, dataType, index, processors, conversion));
                }
                context.getChecker().afterPositionProperty(field, fieldType, index);
            } else {
                context.getChecker().unknownProperty(field, fieldType);
            }
        }
        return unmodifiableList(result);
    }

    List<Property<?, D>> build(Class<? extends Segment> beanType) throws BeanException {
        return getProperties(beanType, context);
    }

}
