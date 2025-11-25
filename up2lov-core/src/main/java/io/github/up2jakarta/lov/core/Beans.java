package io.github.up2jakarta.lov.core;

import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.stream;

public abstract class Beans {

    public static final Type[] NO_TYPES = {};

    private static String typeName(Class<?> type) {
        if (type.isAnonymousClass()) {
            final String name = type.getName();
            final int i = name.lastIndexOf('$');
            return name.substring(i + 1);
        }
        return type.getSimpleName();
    }

    private static StringBuilder typeName(Class<?> type, char delimiter) {
        final StringBuilder sb = new StringBuilder(typeName(type));
        while ((type = type.getEnclosingClass()) != null) {
            sb.insert(0, delimiter).insert(0, typeName(type));
        }
        return sb;
    }

    private static void permittedTypes(Class<?>[] types, Consumer<Class<?>> collector) {
        if (types != null) {
            for (final Class<?> type : types) {
                collector.accept(type);
                permittedTypes(type.getPermittedSubclasses(), collector);
            }
        }
    }

    private static AccessException translate(Member origin, Throwable cause) {
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

    private static Type[] resolveIArguments(Class<?> type, Class<?> expected, final Type... arguments) {
        // Safe findFirst: Java 17 does not support multiple generic interfaces
        return stream(type.getGenericInterfaces())
                .filter(i -> i instanceof ParameterizedType)
                .map(i -> (ParameterizedType) i)
                .filter(i -> expected.isAssignableFrom((Class<?>) i.getRawType()))
                .findFirst()
                .map(c -> {
                    var cArguments = resolveArguments(type, c, arguments);
                    return resolveTArguments((Class<?>) c.getRawType(), expected, cArguments);
                }).orElseGet(() -> {
                    var cArguments = arguments;
                    final Type superType = type.getGenericSuperclass();
                    if (superType != null) {
                        if (type.getGenericSuperclass() instanceof ParameterizedType pType) {
                            cArguments = resolveArguments(type, pType, arguments);
                        }
                        return resolveIArguments(type.getSuperclass(), expected, cArguments);
                    }
                    return NO_TYPES;
                });
    }

    private static Type[] resolveTArguments(Class<?> type, Class<?> expected, Type... arguments) {
        final Class<?> superType = type.getSuperclass();
        if (type == expected) {
            return arguments;
        }
        final Type gType = type.getGenericSuperclass();
        if (gType instanceof ParameterizedType pType) {
            arguments = resolveArguments(type, pType, arguments);
        }
        if (expected == superType) {
            return arguments;
        }
        if (superType == null) {
            return resolveIArguments(type, expected, arguments);
        }
        return resolveTArguments(superType, expected, arguments);
    }

    public static CharSequence getTypeName(Class<?> type) {
        return typeName(type, '.');
    }

    public static String getClassName(Class<?> type) {
        return typeName(type, '$').insert(0, ".").insert(0, type.getPackageName()).toString();
    }

    public static Stream<String> getPermittedTypes(Class<?>... types) {
        final List<Class<?>> result = new LinkedList<>();
        permittedTypes(types, result::add);
        return result.stream().map(Beans::getClassName);
    }

    public static String capitalize(String fieldName) {
        return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public static <T> T[] concat(T[] source, T value) {
        final T[] values = copyOf(source, source.length + 1);
        values[source.length] = value;
        return values;
    }

    public static Type[] resolveArguments(Class<?> type, ParameterizedType pType, Type... arguments) {
        final Type[] typeParameters = type.getTypeParameters();
        var actualArguments = pType.getActualTypeArguments();
        final Type[] result = new Type[actualArguments.length];
        for (var i = 0; i < actualArguments.length; i++) {
            final Type argument = actualArguments[i];
            if (argument instanceof TypeVariable<?>) {
                for (var j = 0; j < typeParameters.length; j++) {
                    final Type parameter = typeParameters[j];
                    if (parameter == argument) {
                        result[i] = arguments[j];
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

    public static Type getPropertyType(AccessibleObject property, Type... arguments) {
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
                    return arguments[i];
                }
            }
        }
        return defaultClass;
    }

    public static Type[] getPropertyArguments(AccessibleObject property, Type... arguments) {
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

    public static <F> Type[] getTypeArguments(Class<?> type, Class<F> expected, Type... arguments) {
        if (expected.getTypeParameters().length == 0) {
            return NO_TYPES;
        }
        if (!expected.isAssignableFrom(type) || type.getTypeParameters().length != arguments.length) {
            return expected.getTypeParameters();
        }
        if (expected.isInterface()) {
            return resolveIArguments(type, expected, arguments);
        }
        return resolveTArguments(type, expected, arguments);
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

    public static <A extends AccessibleObject & Member> A setAccessible(A source) {
        try {
            source.setAccessible(true);
            return source;
        } catch (Exception cause) {
            final String locator = (source instanceof Constructor<?>) ? "new" : source.getName();
            throw new AccessException(source.getDeclaringClass(), locator, cause.getMessage());
        }
    }

    public static <T> Constructor<T> getDeclaredConstructor(Class<T> type, Class<?>... types) throws BeanException {
        try {
            final Constructor<T> constructor = type.getDeclaredConstructor(types);
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception cause) {
            throw new BeanException(type, "new", cause.getMessage());
        }
    }

    public static <T> Constructor<T> getDefaultConstructor(Class<T> type) throws BeanException {
        try {
            final Constructor<T> constructor;
            if (type.isRecord()) {
                final Class<?>[] types = stream(type.getDeclaredFields()).map(Field::getType).toArray(Class<?>[]::new);
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

    public static <T> T newInstance(Constructor<T> constructor, Object... arguments) throws AccessException {
        try {
            return constructor.newInstance(arguments);
        } catch (Exception ex) {
            throw translate(constructor, ex);
        }
    }

    public static <V> void setValue(Object bean, V value, Method setter) throws AccessException {
        try {
            setter.invoke(bean, value);
        } catch (Exception cause) {
            throw translate(setter, cause);
        }
    }

    public static <V> void setValue(Object bean, V value, Field field) throws AccessException {
        try {
            field.set(bean, value);
        } catch (Exception cause) {
            throw translate(field, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(Object bean, Method getter) throws AccessException {
        try {
            return (V) getter.invoke(bean);
        } catch (Exception cause) {
            throw translate(getter, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(Object bean, Field field) throws AccessException {
        try {
            return (V) field.get(bean);
        } catch (Exception cause) {
            throw translate(field, cause);
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

    @SuppressWarnings("unchecked")
    public static <E> Class<E> cast(Class<?> type) {
        return (Class<E>) type;
    }

}
