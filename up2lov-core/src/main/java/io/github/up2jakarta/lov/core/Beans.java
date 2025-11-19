package io.github.up2jakarta.lov.core;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.stream;

public abstract class Beans {

    public static final Type[] NO_TYPES = {};

    private static String getName(Class<?> type) {
        if (type.isAnonymousClass()) {
            final String name = type.getName();
            final int i = name.lastIndexOf('$');
            return name.substring(i + 1);
        }
        return type.getSimpleName();
    }

    private static StringBuilder getTypeName(Class<?> type, char delimiter) {
        final StringBuilder sb = new StringBuilder(getName(type));
        while ((type = type.getEnclosingClass()) != null) {
            sb.insert(0, delimiter).insert(0, getName(type));
        }
        return sb;
    }

    private static void getPermittedTypes(Class<?>[] types, Consumer<Class<?>> collector) {
        if (types != null) {
            for (final Class<?> type : types) {
                collector.accept(type);
                getPermittedTypes(type.getPermittedSubclasses(), collector);
            }
        }
    }

    private static AccessException of(Member origin, Throwable cause) {
        if (cause instanceof InvocationTargetException ex) {
            cause = ex.getTargetException();
        }
        if (cause instanceof AccessException ex) {
            return ex;
        }
        final Class<?> source = origin.getDeclaringClass();
        final String locator = (origin instanceof Constructor<?>) ? "new" : origin.getName();
        if (cause instanceof BeanException ex && source.equals(ex.getSource())) {
            return new AccessException(source, locator, ex.getMessage());
        }
        return new AccessException(source, locator, cause);
    }

    public static CharSequence getTypeName(Class<?> type) {
        return getTypeName(type, '.');
    }

    public static String getClassName(Class<?> type) {
        return getTypeName(type, '$').insert(0, ".").insert(0, type.getPackageName()).toString();
    }

    public static Stream<String> getPermittedTypes(Class<?>... types) {
        final List<Class<?>> result = new LinkedList<>();
        getPermittedTypes(types, result::add);
        return result.stream().map(Beans::getClassName);
    }

    public static Type[] getInterfaceArguments(Class<?> beanType, Class<?> finalType, final Type... typeArguments) {
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

    public static Type[] getClassArguments(Class<?> beanType, Class<?> finalType, Type... typeArguments) {
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
        if (superType == null) {
            return getInterfaceArguments(beanType, finalType, typeArguments);
        }
        return getClassArguments(superType, finalType, typeArguments);
    }

    @SuppressWarnings("unchecked")
    public static <E> Class<E> cast(Class<?> type) {
        return (Class<E>) type;
    }

    public static Type[] resolveArguments(Class<?> type, ParameterizedType pType, Type[] typeArguments) {
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

    public static Class<?> getPropertyClass(AccessibleObject property, Type type) {
        if (type instanceof Class<?> fc) {
            return fc;
        }
        if ((type instanceof ParameterizedType tv) && tv.getRawType() instanceof Class<?> fc) {
            return fc;
        }
        if (property instanceof Field field) {
            return field.getType();
        } else if (property instanceof Method getter) {
            return getter.getReturnType();
        }
        return void.class;
    }

    public static Type getPropertyType(AccessibleObject property, Type... typeArguments) {
        final Type propertyType;
        final Class<?> propertyClass;
        final Class<?> defaultClass;
        if (property instanceof Field field) {
            propertyClass = field.getDeclaringClass();
            propertyType = field.getGenericType();
            defaultClass = field.getType();
        } else if (property instanceof Method getter) {
            propertyClass = getter.getDeclaringClass();
            propertyType = getter.getGenericReturnType();
            defaultClass = getter.getReturnType();
        } else {
            return void.class;
        }
        if (propertyType instanceof TypeVariable<?>) {
            final Type[] typeParameters = propertyClass.getTypeParameters();
            for (var i = 0; i < typeParameters.length; i++) {
                if (typeParameters[i] == propertyType) {
                    return typeArguments[i];
                }
            }
        }
        return defaultClass;
    }

    public static Type[] getPropertyArguments(AccessibleObject property, Type[] arguments) {
        if (property instanceof Field field) {
            if (field.getGenericType() instanceof ParameterizedType type) {
                return resolveArguments(field.getDeclaringClass(), type, arguments);
            }
        } else if (property instanceof Method getter) {
            if (getter.getGenericReturnType() instanceof ParameterizedType type) {
                return resolveArguments(getter.getDeclaringClass(), type, arguments);
            }
        }
        return NO_TYPES;
    }

    public static String capitalize(String fieldName) {
        return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public static Type[] getTypeArguments(Class<?> beanType, Class<?> finalType) throws BeanException {
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

    public static Type[] getTypeArguments(Type beanType) {
        if (beanType instanceof ParameterizedType pType) {
            return pType.getActualTypeArguments();
        }
        return NO_TYPES;
    }

    public static Method getMethod(Class<?> type, String name, String attr, String desc, Class<?>... types) throws BeanException {
        try {
            final Method method = type.getDeclaredMethod(name, types);
            method.setAccessible(true);
            return method;
        } catch (Exception ignore) {
        }
        try {
            final Method method = type.getMethod(name, types);
            method.setAccessible(true);
            return method;
        } catch (Exception ex) {
            throw new BeanException(type, attr, desc + " not found");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(BeanContext context, Class<?> type, String name) throws BeanException {
        try {
            final T bean = (T) ((name.isEmpty()) ? context.getBean(type) : context.getBean(type, name));
            if (bean instanceof BeanAware bc) {
                bc.setContext(context);
            }
            return bean;
        } catch (Exception e) {
            throw new BeanException(type, "qualified bean must be found");
        }
    }

    public static Method getAccessibleSetter(Class<?> type, Field field, Class<?> ft) throws BeanException {
        final String fn = field.getName();
        final String pName = capitalize(fn);
        try {
            return getMethod(type, "set" + pName, fn, "setter", ft);
        } catch (BeanException ignore) {
            return getMethod(type, "set" + pName, fn, "setter", field.getType());
        }
    }

    public static Method getAccessibleGetter(Class<?> type, Field field, Class<?> ft) throws BeanException {
        final String fn = field.getName();
        if (type.isRecord()) {
            return getMethod(type, fn, fn, "getter");
        }
        final String pName = capitalize(fn);
        if (ft == Boolean.class || ft == boolean.class) {
            try {
                return getMethod(type, "is" + pName, fn, "getter");
            } catch (BeanException ignore) {
            }
        }
        return getMethod(type, "get" + pName, fn, "getter");
    }

    public static boolean isInnerType(Class<?> type) {
        return type.getEnclosingClass() != null && !isStatic(type.getModifiers());
    }

    public static <T> Constructor<T> getDefaultConstructor(Class<T> type) throws BeanException {
        try {
            final Constructor<T> constructor;
            if (type.isRecord()) {
                final Field[] fields = type.getDeclaredFields();
                final Class<?>[] types = new Class<?>[fields.length];
                for (var i = 0; i < fields.length; i++) {
                    types[i] = fields[i].getType();
                }
                constructor = type.getDeclaredConstructor(types);
            } else if (isInnerType(type)) {
                constructor = type.getDeclaredConstructor(type.getEnclosingClass());
            } else {
                constructor = type.getDeclaredConstructor();
            }
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception cause) {
            throw new BeanException(type, "new", cause.getMessage());
        }
    }

    public static <T> T[] concat(T[] source, T value) {
        final T[] values = Arrays.copyOf(source, source.length + 1);
        values[source.length] = value;
        return values;
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... arguments) throws AccessException {
        try {
            return constructor.newInstance(arguments);
        } catch (Exception ex) {
            throw of(constructor, ex);
        }
    }

    public static <V> void setValue(Object bean, V value, Method setter) throws AccessException {
        try {
            setter.invoke(bean, value);
        } catch (Exception cause) {
            throw of(setter, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(Object bean, Method getter) throws AccessException {
        try {
            return (V) getter.invoke(bean);
        } catch (Exception cause) {
            throw of(getter, cause);
        }
    }

    public static <V> void setValue(Object bean, V value, Field field) throws AccessException {
        try {
            field.set(bean, value);
        } catch (Exception cause) {
            throw of(field, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(Object bean, Field field) throws AccessException {
        try {
            return (V) field.get(bean);
        } catch (Exception cause) {
            throw of(field, cause);
        }
    }

}
