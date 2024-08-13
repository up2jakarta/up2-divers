package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Processor;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;
import io.github.up2jakarta.csv.processor.SingleArgumentTransformer;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;

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

    private static String[] lookupArguments(Annotation annotation, Processor processor) throws BeanException {
        for (final Method method : annotation.annotationType().getDeclaredMethods()) {
            var overriding = method.getAnnotation(Processor.Override.class);
            if (overriding != null && overriding.value() == processor.value()) {
                var value = Beans.invoke(annotation, method, Object.class);
                if (value instanceof String[] args && args.length != 0) {
                    return args;
                }
                if (value instanceof String arg && SingleArgumentTransformer.class.isAssignableFrom(processor.value())) {
                    return new String[]{arg};
                }
            }
        }
        return Beans.NO_ARGUMENTS;
    }

    private static Fragment getAndCheckFragment(Field field) throws BeanException {
        final Fragment csv = field.getAnnotation(Fragment.class);
        if (csv == null) {
            throw new BeanException(field, "should be annotated by @Fragment");
        }
        if (csv.value() < 0) {
            throw new BeanException(field, "@Fragment[value] should be positive");
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
            throw new BeanException(beanType, field, "must starts with an lowercase character");
        }
        if (!Modifier.isPrivate(field.getModifiers()) && !Modifier.isProtected(field.getModifiers())) {
            throw new BeanException(beanType, field, "should be private or protected");
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
        checkInner(beanType);
    }

    static ConfigurableTransformer[] getTransformers(BeanContext context, Field field) throws BeanException {
        final List<ConfigurableTransformer> result = new LinkedList<>();
        // Add Processors
        for (final Processor processor : field.getAnnotationsByType(Processor.class)) {
            result.add(TransformerWrapper.of(context, processor));
        }
        // Add Shortcut Annotations
        for (final Annotation annotation : field.getAnnotations()) {
            final Processor[] processors = annotation.annotationType().getAnnotationsByType(Processor.class);
            for (final Processor processor : processors) {
                final String[] arguments = lookupArguments(annotation, processor);
                result.add(TransformerWrapper.of(context, processor, arguments));
            }
        }
        return result.toArray(new ConfigurableTransformer[0]);
    }

    @SuppressWarnings("unchecked")
    static Property<?>[] getProperties(BeanContext context, Class<? extends Segment> beanType, final int offset, Type... typeArguments) throws BeanException {
        final Field[] fields = beanType.getDeclaredFields();
        final List<Property<?>> properties = new LinkedList<>();
        for (final Field field : fields) {
            final Class<?> fieldType = getFieldType(field, typeArguments);
            final Position position = getAndCheckPosition(field);
            if (position != null) {
                if (!String.class.equals(fieldType)) {
                    throw new BeanException(beanType, field, "type should be String");
                }
                checkField(beanType, field);
                final ConfigurableTransformer[] transformers = getTransformers(context, field);
                properties.add(new StringProperty(field, offset + position.value(), transformers));

            } else if (Segment.class.isAssignableFrom(fieldType)) {
                checkField(beanType, field);
                final Fragment csv = getAndCheckFragment(field);
                final Class<? extends Segment> fragmentType = (Class<? extends Segment>) fieldType;
                checkInner(fragmentType);
                final int fragmentOffset = offset + csv.value();
                final Property<?>[] fragmentProperties = getProperties(context, fragmentType, fragmentOffset);
                properties.add(new FragmentProperty(fragmentType, field, fragmentOffset, fragmentProperties));
            }
        }
        final Class<?> superClass = beanType.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            final Type[] arguments = Beans.getTypeArguments(beanType);
            final Property<?>[] superProperties = getProperties(context, (Class<? extends Segment>) superClass, offset, arguments);
            properties.addAll(asList(superProperties));
        }
        return properties.toArray(new Property[0]);
    }

}
