package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.*;
import io.github.up2jakarta.csv.core.MapperFactory.FragmentProperty;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.core.Beans.getBean;
import static java.util.Arrays.asList;

final class BeanSupport {

    private BeanSupport() {
    }

    private static Class<?> getFieldType(Field field, Type[] typeArguments) {
        final Type fieldType = field.getGenericType();
        if (fieldType instanceof TypeVariable<?>) {
            final Type[] typeParameters = field.getDeclaringClass().getTypeParameters();
            for (var i = 0; i < typeParameters.length; i++) {
                if (typeParameters[i] == fieldType) {
                    return (Class<?>) typeArguments[i];
                }
            }
        }
        return field.getType();
    }

    private static Fragment getAndCheckFragment(Field field) throws BeanException {
        final Fragment csv = field.getAnnotation(Fragment.class);
        if (csv != null && csv.value() < 0) {
            throw new BeanException(field, "@Fragment[value] must be positive");
        }
        if (csv != null && !Segment.class.isAssignableFrom(field.getType())) {
            throw new BeanException(field, "type must implements Segment");
        }
        return csv;
    }

    private static Position getAndCheckPosition(Field field) throws BeanException {
        final Position csv = field.getAnnotation(Position.class);
        if (csv != null && csv.value() < 0) {
            throw new BeanException(field, "@Position[value] must be positive");
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

    @SuppressWarnings("unchecked")
    static <A extends Annotation> List<A> getAnnotationsByType(Class<A> annotationType, AnnotatedElement element) throws BeanException {
        final Method repeatValue = getRepeatableValue(annotationType);
        final List<A> result = new LinkedList<>();
        for (final Annotation annotation : element.getAnnotations()) {
            final Class<? extends Annotation> aType = annotation.annotationType();
            // direct
            if (annotationType.equals(aType)) {
                result.add((A) annotation);
            }
            // indirect
            if (repeatValue != null && repeatValue.getDeclaringClass().equals(aType)) {
                try {
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

    static ProcessorWrapper<?>[] getProcessors(BeanContext context, Field field) throws BeanException {
        final List<ProcessorWrapper<?>> result = new LinkedList<>();
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
        return result.toArray(ProcessorWrapper[]::new);
    }

    static Conversion<?> getConversion(MapperContext context, Field field) throws BeanException {
        final Converter converter = field.getAnnotation(Converter.class);
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
        return context.getConversion(field);
    }

    static Property<?>[] getProperties(Class<? extends Segment> beanType, MapperContext context) throws BeanException {
        if (context.push(beanType)) {
            throw new BeanException(beanType, "cyclic fragment is not allowed");
        }
        final List<Property<?>> properties = new LinkedList<>();
        final Class<?> superClass = beanType.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            final Type[] arguments = Beans.getTypeArguments(beanType);
            //noinspection unchecked
            final Class<? extends Segment> superType = (Class<? extends Segment>) superClass;
            final Property<?>[] superProperties = getProperties(superType, context.with(arguments));
            properties.addAll(asList(superProperties));
        }
        final Field[] fields = beanType.getDeclaredFields();
        final int offset = context.getOffset();
        for (final Field field : fields) {
            final Class<?> fieldType = getFieldType(field, context.getArguments());
            final Position position = getAndCheckPosition(field);
            final Fragment fragment = getAndCheckFragment(field);
            if (position != null) {
                final int index = offset + position.value();
                TechnicalChecker.checkPositionProperty(field);
                final ProcessorWrapper<?>[] processors = getProcessors(context.getContext(), field);
                if (CharSequence.class == fieldType || fieldType == String.class) {
                    properties.add(new StringProperty(field, index, processors));
                } else {
                    final Conversion<?> conversion = getConversion(context, field);
                    checkDefault(field, conversion);
                    properties.add(new ConvertedProperty<>(field, index, processors, conversion));
                }
            } else if (fragment != null) {
                //noinspection unchecked
                final Class<? extends Segment> fragmentType = (Class<? extends Segment>) fieldType;
                final int fragmentOffset = offset + fragment.value();
                TechnicalChecker.checkFragmentProperty(field, fragmentType);
                final Property<?>[] fragmentProperties = getProperties(fragmentType, context.with(fragmentOffset));
                properties.add(new FragmentProperty<>(fragmentType, field, fragmentOffset, fragmentProperties));
            }
        }
        return properties.toArray(Property[]::new);
    }

}
