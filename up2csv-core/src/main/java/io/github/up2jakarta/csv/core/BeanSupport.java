package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.*;
import io.github.up2jakarta.csv.core.MapperFactory.FragmentProperty;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.core.Beans.getBean;
import static io.github.up2jakarta.csv.core.MapperFactory.LOGGER;
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
            throw new BeanException(field, "@Fragment[value] should be positive");
        }
        if (csv != null && !Segment.class.isAssignableFrom(field.getType())) {
            throw new BeanException(field, "type must implements Segment");
        }
        return csv;
    }

    private static Position getAndCheckPosition(Field field) throws BeanException {
        final Position csv = field.getAnnotation(Position.class);
        if (csv != null && csv.value() < 0) {
            throw new BeanException(field, "@Position[value] should be positive");
        }
        return csv;
    }

    private static void checkField(Class<? extends Segment> beanType, Field field) throws BeanException {
        if (Character.isUpperCase(field.getName().charAt(0))) {
            LOGGER.warn("{}[{}] : should starts with an lowercase character", beanType.getSimpleName(), field.getName());
        }
        if (Modifier.isPublic(field.getModifiers())) {
            throw new BeanException(beanType, field, "must not be public");
        }
        if (Modifier.isFinal(field.getModifiers())) {
            throw new BeanException(beanType, field, "must not be final");
        }
        if (Modifier.isStatic(field.getModifiers())) {
            throw new BeanException(beanType, field, "must not be static");
        }
    }

    private static void checkInner(Class<? extends Segment> beanType) throws BeanException {
        if (beanType.getEnclosingClass() != null && !Modifier.isStatic(beanType.getModifiers())) {
            throw new BeanException(beanType, "inner class is not allowed");
        }
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

    static void checkBean(Class<? extends Segment> beanType) throws BeanException {
        if (beanType.isLocalClass()) {
            throw new BeanException(beanType, "local class is not allowed");
        }
        if (beanType.isInterface()) {
            throw new BeanException(beanType, "interface is not allowed");
        }
        if (Modifier.isAbstract(beanType.getModifiers())) {
            throw new BeanException(beanType, "abstract class is not allowed");
        }
        if (beanType.getTypeParameters().length != 0) {
            throw new BeanException(beanType, "generic class is not allowed");
        }
        if (beanType.isRecord()) {
            throw new BeanException(beanType, "record class is not allowed");
        }
        checkInner(beanType);
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
        return result.toArray(new ProcessorWrapper[0]);
    }

    static Conversion<?> getConversion(BeanContext context, Field field) throws BeanException {
        final Converter converter = field.getAnnotation(Converter.class);
        final Error error = field.getAnnotation(Error.class);
        if (converter != null) {
            final TypeConverter<?> tConverter = Beans.getBean(context, converter.value());
            if (!tConverter.getSupportedType().isAssignableFrom(field.getType())) {
                throw new BeanException(field, "@Converter[value] does support " + field.getType().getSimpleName());
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
                var conversionResolver = (ConversionResolver<Annotation>) Beans.getBean(context, resolver.value());
                final Conversion<?> conversion = conversionResolver.resolve(annotation, field);
                return Conversion.of(conversion, error);
            }
        }
        throw new BeanException(field, "must be annotated with @Converter or one of its shortcuts");
    }

    static Property<?>[] getProperties(Class<? extends Segment> beanType, final BeanStack stack) throws BeanException {
        if (stack.push(beanType)) {
            throw new BeanException(beanType, "cyclic fragment is not allowed");
        }
        final List<Property<?>> properties = new LinkedList<>();
        final Class<?> superClass = beanType.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            final Type[] arguments = Beans.getTypeArguments(beanType);
            //noinspection unchecked
            final Class<? extends Segment> fragmentType = (Class<? extends Segment>) superClass;
            final Property<?>[] superProperties = getProperties(fragmentType, stack.with(arguments));
            properties.addAll(asList(superProperties));
        }
        final Field[] fields = beanType.getDeclaredFields();
        final int offset = stack.getOffset();
        for (final Field field : fields) {
            final Class<?> fieldType = getFieldType(field, stack.getArguments());
            final Position position = getAndCheckPosition(field);
            final Fragment fragment = getAndCheckFragment(field);
            if (position != null) {
                checkField(beanType, field);
                final ProcessorWrapper<?>[] processors = getProcessors(stack.getContext(), field);
                if (CharSequence.class == fieldType || fieldType == String.class) {
                    properties.add(new StringProperty(field, offset + position.value(), processors));
                } else {
                    final Conversion<?> conversion = getConversion(stack.getContext(), field);
                    checkDefault(field, conversion);
                    properties.add(new ConvertedProperty<>(field, offset + position.value(), processors, conversion));
                }
            } else if (fragment != null) {
                checkField(beanType, field);
                //noinspection unchecked
                final Class<? extends Segment> fragmentType = (Class<? extends Segment>) fieldType;
                checkInner(fragmentType);
                final int fragmentOffset = offset + fragment.value();
                final Property<?>[] fragmentProperties = getProperties(fragmentType, stack.with(fragmentOffset));
                properties.add(new FragmentProperty<>(fragmentType, field, fragmentOffset, fragmentProperties));
            }
        }
        return properties.toArray(new Property[0]);
    }

}
