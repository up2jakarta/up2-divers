package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.TypeConverter;
import jakarta.validation.Valid;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.core.Beans.*;
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

    private static Method getRepeatableValue(Class<? extends Annotation> annotationType) throws BeanException {
        final Repeatable repeatable = annotationType.getDeclaredAnnotation(Repeatable.class);
        if (repeatable != null) {
            return Beans.getMethod(repeatable.value(), "value", "class", "value()");
        }
        return null;
    }

    private static <A extends Annotation> void walkPath(
            List<? extends Property<?, ?>> properties, Class<A> annotationType,
            BiConsumer<A, Property<?, ?>[]> collector, Property<?, ?>... stack
    ) throws BeanException {
        for (final Property<?, ?> property : properties) {
            final Field field = property.field;
            final A annotation = field.getAnnotation(annotationType);
            if (property instanceof BeanProperty<?, ?> fp) {
                if (annotation != null) {
                    throw new BeanException(field, "must not be annotated with @" + annotationType.getSimpleName());
                }
                walkPath(fp.toCollection(), annotationType, collector, concat(stack, property));
            } else if (annotation != null) {
                collector.accept(annotation, concat(stack, property));
            }
        }
    }

    static <A extends Annotation> Property<?, ?>[] uniquePath(
            Class<? extends Segment> sType, Class<A> aType, List<? extends Property<?, ?>> properties
    ) throws BeanException {
        final Map<A, Property<?, ?>[]> found = new LinkedHashMap<>();
        walkPath(properties, aType, found::put);
        if (found.isEmpty()) {
            return null;
        }
        if (found.size() == 1) {
            return found.entrySet().iterator().next().getValue();
        }
        throw new BeanException(sType, "multiple @" + aType.getSimpleName() + " are found");
    }

    static <T> T getDefault(Field field, PropertyConverter<T> conversion) throws BeanException {
        final Up2Default defaultValue = field.getAnnotation(Up2Default.class);
        if (defaultValue != null) {
            try {
                return conversion.apply(defaultValue.value());
            } catch (Exception ex) {
                throw new BeanException(field, "@Up2Default[value] cannot be converted");
            }
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
                } catch (Exception t) {
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
                final Class<? extends InputProcessor<?>> pType = processor.value();
                final Type[] types = Beans.getTypeArguments(pType, InputProcessor.class);
                if (types.length == 0 || aType != types[0]) {
                    final String aName = aType.getSimpleName();
                    throw new BeanException(aType, "@Processor[value] must implements InputProcessor<" + aName + ">");
                }
                final InputProcessor<? extends Annotation> delegate = getBean(context, pType);
                //noinspection rawtypes,unchecked
                result.add(new ProcessorWrapper(delegate, processor.skip(), annotation));
            }
        }
        return unmodifiableList(result);
    }

    @SuppressWarnings("unchecked")
    private Conversion<?> getConversion(MapperContext<D> context, Field field, Class<?> type) throws BeanException {
        final Up2Converter converter = field.getAnnotation(Up2Converter.class);
        final Error error = field.getAnnotation(Error.class);
        if (converter != null) {
            final TypeConverter<Object> tConverter = (TypeConverter<Object>) getBean(context.getContext(), converter.value());
            if (!tConverter.getSupportedType().isAssignableFrom(field.getType())) {
                throw new BeanException(field, "@Converter[value] does not support " + field.getType().getSimpleName());
            }
            if (error == null) {
                var p = PropertyConverter.of(tConverter::parse, tConverter.getErrorSeverity(), tConverter.getErrorCode());
                var f = PropertyFormatter.of(tConverter::format, tConverter.getErrorSeverity(), tConverter.getErrorCode());
                return new Conversion<>(p, f);
            }
            return new Conversion<>(tConverter::parse, tConverter::format, error);
        }
        for (final Annotation annotation : field.getAnnotations()) {
            final Resolver resolver = annotation.annotationType().getAnnotation(Resolver.class);
            if (resolver != null) {
                var cResolver = (ConversionResolver<Annotation>) getBean(context.getContext(), resolver.value());
                final PropertyConverter<Object> p = (PropertyConverter<Object>) cResolver.forParsing(annotation, field);
                final PropertyFormatter<Object> f = (PropertyFormatter<Object>) cResolver.forFormatting(annotation, field);
                return new Conversion<>(p, f, error);
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
                final int fo = offset + fragment.value();
                context.getChecker().beforeFragmentProperty(field, fType);
                final List<Property<?, D>> fProps = getProperties(fType, context.with(field, fo, fType));
                context.getChecker().afterFragmentProperty(field, fType);
                final ValidationContext fContext = context.getValidation(field);
                result.add(new BeanProperty<>(fType, dataType, field, fo, fragment.nullable(), fContext, fProps));
            } else if (position != null) {
                final D dataType = context.getDataType(field);
                final int index = offset + position.value();
                context.getChecker().beforePositionProperty(field, fieldType, index);
                final List<ProcessorWrapper<?, D>> processors = getProcessors(context.getContext(), field);
                if (CharSequence.class == fieldType || fieldType == String.class) {
                    result.add(new StringProperty<>(field, dataType, index, processors));
                } else {
                    final Conversion<?> conversion = getConversion(context, field, fieldType);
                    result.add(new ObjectProperty<>(field, fieldType, dataType, index, processors, conversion));
                }
                context.getChecker().afterPositionProperty(field, fieldType, index);
            } else {
                context.getChecker().unknownProperty(field, fieldType);
            }
        }
        return result;
    }

    List<Property<?, D>> build(Class<? extends Segment> beanType) throws BeanException {
        final List<Property<?, D>> result = getProperties(beanType, context);
        return unmodifiableList(result);
    }

}
