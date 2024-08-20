package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;

import java.lang.reflect.*;

import static java.util.Arrays.stream;

public final class Beans {

    public static final Type[] NO_TYPES = {};

    private Beans() {
    }

    private static Type[] getInterfaceArguments(Class<?> beanType, Class<?> finalType, final Type... typeArguments) {
        // Safe findFirst: Java 17 does not support multiple generic interfaces
        return stream(beanType.getGenericInterfaces())
                .filter(i -> i instanceof ParameterizedType)
                .map(i -> (ParameterizedType) i)
                .filter(i -> finalType.isAssignableFrom((Class<?>) i.getRawType()))
                .findFirst()
                .map(c -> {
                    var cArguments = resolveArguments(beanType, c, typeArguments);
                    return getClassArguments((Class<?>) c.getRawType(), finalType, cArguments);
                }).orElseGet(() -> {
                    var cArguments = typeArguments;
                    final Type superType = beanType.getGenericSuperclass();
                    if (superType != null) {
                        if (beanType.getGenericSuperclass() instanceof ParameterizedType pType) {
                            cArguments = resolveArguments(beanType, pType, typeArguments);
                        }
                        return getInterfaceArguments(beanType.getSuperclass(), finalType, cArguments);
                    }
                    return NO_TYPES;
                });
    }

    private static Type[] getClassArguments(Class<?> beanType, Class<?> finalType, Type... typeArguments) {
        final Class<?> superType = beanType.getSuperclass();
        if (beanType == finalType) {
            return typeArguments;
        }
        final Type gType = beanType.getGenericSuperclass();
        if (gType instanceof ParameterizedType pType) {
            typeArguments = resolveArguments(beanType, pType, typeArguments);
        }
        if (finalType == superType) {
            return typeArguments;
        }
        return getClassArguments(superType, finalType, typeArguments);
    }

    private static Type[] resolveArguments(Class<?> type, ParameterizedType pType, Type[] typeArguments) {
        final Type[] typeParameters = type.getTypeParameters();
        var actualArguments = pType.getActualTypeArguments();
        final Type[] result = new Type[actualArguments.length];
        for (var i = 0; i < actualArguments.length; i++) {
            final Type argument = actualArguments[i];
            if (argument instanceof TypeVariable<?>) {
                for (var j = 0; j < typeParameters.length; j++) {
                    final Type parameter = typeParameters[j];
                    if (parameter == argument) {
                        result[i] = typeArguments[j];
                    }
                }
            } else {
                result[i] = argument;
            }
        }
        return result;
    }

    static Type[] getTypeArguments(Class<?> beanType, Class<?> finalType) throws BeanException {
        if (finalType.getTypeParameters().length == 0 || beanType == Object.class) {
            return NO_TYPES;
        }
        if (beanType.getTypeParameters().length != 0) {
            throw new BeanException(beanType, "the root bean can not be generic");
        }
        if (finalType.isInterface()) {
            return getInterfaceArguments(beanType, finalType);
        }
        return getClassArguments(beanType, finalType);
    }

    static Type[] getTypeArguments(Class<?> beanType) {
        final Type genericType = beanType.getGenericSuperclass();
        if (genericType instanceof ParameterizedType pType) {
            return pType.getActualTypeArguments();
        }
        return NO_TYPES;
    }

    public static <T> T getBean(BeanContext context, Class<T> beanType) throws BeanException {
        try {
            return context.getBean(beanType);
        } catch (Throwable e) {
            throw new BeanException(beanType, "qualified bean must be found");
        }
    }

    public static String capitalize(String fieldName) {
        return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public static Method getAccessibleSetter(Field field) throws BeanException {
        final String propertyName = capitalize(field.getName());
        final Class<?> originType = field.getDeclaringClass();
        try {
            final Method getter = originType.getDeclaredMethod("set" + propertyName, field.getType());
            if (!Modifier.isPublic(getter.getModifiers())) {
                throw new BeanException(originType, field, "setter should be public");
            }
            return getter;
        } catch (Throwable ex) {
            throw new BeanException(originType, field, "setter not found");
        }
    }

    public static <T> Constructor<T> getDefaultConstructor(Class<T> type) throws BeanException {
        try {
            final Constructor<T> constructor = type.getDeclaredConstructor();
            if (!Modifier.isPublic(constructor.getModifiers())) {
                throw new BeanException(type, "default constructor should be public");
            }
            return constructor;
        } catch (Throwable e) {
            throw new BeanException(type, e.getMessage());
        }
    }

    public static <T> T newInstance(Constructor<T> constructor) throws BeanException {
        try {
            return constructor.newInstance();
        } catch (Throwable ex) {
            throw new BeanException(constructor.getDeclaringClass(), ex.getMessage());
        }
    }

    public static <V> void setValue(Object bean, V value, Method setter) throws BeanException {
        try {
            setter.invoke(bean, value);
        } catch (Throwable ex) {
            throw new BeanException(bean.getClass(), setter, ex.getMessage());
        }
    }

}
