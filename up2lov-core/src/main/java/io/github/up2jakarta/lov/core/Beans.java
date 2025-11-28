package io.github.up2jakarta.lov.core;

import java.lang.reflect.*;
import java.util.function.Predicate;

import static io.github.up2jakarta.lov.core.Defaults.wrap;
import static io.github.up2jakarta.lov.core.Localizable.CONSTRUCTOR;
import static java.lang.reflect.Modifier.isStatic;

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

    private static AccessException translate(Member origin, Throwable cause) {
        if (cause instanceof AccessException ex) {
            return ex;
        }
        final Class<?> source = origin.getDeclaringClass();
        final String locator = (origin instanceof Constructor<?>) ? CONSTRUCTOR : origin.getName();
        if (cause instanceof BeanException ex && source.equals(ex.getSource())) {
            return new AccessException(source, locator, ex.getMessage());
        }
        return new AccessException(source, locator, cause);
    }

    private static Type[] interfaceArguments(Class<?> type, Class<?> expected, final Type... arguments) {
        if (type == expected) {
            return arguments;
        }
        for (final Type i : type.getGenericInterfaces()) {
            if ((i instanceof Class<?> c) && expected.isAssignableFrom(c)) {
                return interfaceArguments(c, expected);
            }
            if ((i instanceof ParameterizedType p) && (p.getRawType() instanceof Class<?> c)) {
                if (expected.isAssignableFrom(c)) {
                    return interfaceArguments(c, expected, resolveArguments(type, p, arguments));
                }
            }
        }
        final Class<?> superType = type.getSuperclass();
        if (type.getGenericSuperclass() instanceof ParameterizedType pt) {
            return interfaceArguments(superType, expected, resolveArguments(type, pt, arguments));
        }
        return interfaceArguments(superType, expected);
    }

    private static Type[] classArguments(Class<?> type, Class<?> expected, Type... arguments) {
        final Type gType = type.getGenericSuperclass();
        if (gType instanceof ParameterizedType pType) {
            arguments = resolveArguments(type, pType, arguments);
        }
        final Class<?> superType = type.getSuperclass();
        if (expected == superType) {
            return arguments;
        }
        return classArguments(superType, expected, arguments);
    }

    protected static StringBuilder getTypeName(Class<?> type, char delimiter) {
        final StringBuilder sb = new StringBuilder(typeName(type));
        while ((type = type.getEnclosingClass()) != null) {
            sb.insert(0, delimiter).insert(0, typeName(type));
        }
        return sb;
    }

    public static String getTypeName(Class<?> type) {
        return getTypeName(type, '.').toString();
    }

    public static String capitalize(String fieldName) {
        return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public static Type resolveArgument(Type type) {
        if (type instanceof WildcardType wt) {
            final Type[] ubs = wt.getUpperBounds();
            if (ubs.length == 1) {
                return ubs[0];
            }
        }
        return type;
    }

    public static Type[] resolveArguments(Class<?> type, ParameterizedType pType, Type... arguments) {
        final Type[] parameters = type.getTypeParameters();
        final Type[] args = pType.getActualTypeArguments();
        final Type[] result = new Type[args.length];
        for (var i = 0; i < args.length; i++) {
            final Type arg = resolveArgument(args[i]);
            if (arg instanceof TypeVariable<?> tv) {
                for (var j = 0; j < parameters.length; j++) {
                    final Type pt = parameters[j];
                    if (tv == pt || ((pt instanceof TypeVariable<?> pv) && pv.getName().equals(tv.getName()))) {
                        result[i] = arguments[j];
                    }
                }
            } else {
                result[i] = arg;
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
            final Type[] parameters = propertyClass.getTypeParameters();
            for (var i = 0; i < parameters.length; i++) {
                if (parameters[i] == propertyType) {
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

    public static Class<?> getPropertyType(Class<?> type, String name, Predicate<AnnotatedElement> filter) {
        final String gn = "get" + capitalize(name);
        final String fn = "is" + capitalize(name);
        while (type != null) {
            for (final Field p : type.getDeclaredFields()) {
                if (!isStatic(p.getModifiers()) && filter.test(p) && name.equals(p.getName())) {
                    return p.getType();
                }
            }
            for (final Method p : type.getDeclaredMethods()) {
                if (!isStatic(p.getModifiers()) && filter.test(p) && p.getParameterCount() == 0) {
                    if (gn.equals(p.getName()) || (wrap(p.getReturnType()) == Boolean.class && fn.equals(p.getName()))) {
                        return p.getReturnType();
                    }
                }
            }
            type = type.getSuperclass();
        }
        return null;
    }

    public static Type[] getTypeArguments(Class<?> type, Class<?> superType, Type... arguments) {
        if (superType.getTypeParameters().length == 0) {
            return NO_TYPES;
        }
        if (!superType.isAssignableFrom(type) || type.getTypeParameters().length != arguments.length) {
            return superType.getTypeParameters();
        }
        if (type == superType) {
            return arguments;
        }
        if (superType.isInterface()) {
            return interfaceArguments(type, superType, arguments);
        }
        return classArguments(type, superType, arguments);
    }

    public static Type[] getTypeArguments(Type beanType) {
        if (beanType instanceof ParameterizedType pType) {
            return pType.getActualTypeArguments();
        }
        return NO_TYPES;
    }

    public static <T> Class<T> getTypeArgument(Class<?> type, Class<?> superType, int index, Class<T> defaultType) {
        final Type[] ts = getTypeArguments(type, superType);
        if (index < ts.length) {
            final Type argument = ts[index];
            if (argument instanceof Class<?> fc) {
                return cast(fc);
            }
            if ((argument instanceof ParameterizedType pt) && pt.getRawType() instanceof Class<?> fc) {
                return cast(fc);
            }
        }
        return defaultType;
    }

    public static <T> Constructor<T> getDeclaredConstructor(Class<T> type, Class<?>... types) throws BeanException {
        try {
            final Constructor<T> constructor = type.getDeclaredConstructor(types);
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception cause) {
            throw new BeanException(type, CONSTRUCTOR, cause.getMessage());
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... arguments) throws AccessException {
        try {
            return constructor.newInstance(arguments);
        } catch (InvocationTargetException cause) {
            throw translate(constructor, cause.getTargetException());
        } catch (Exception ex) {
            throw translate(constructor, ex);
        }
    }

    public static <V> void setValue(Object bean, V value, Method setter) throws AccessException {
        try {
            setter.invoke(bean, value);
        } catch (InvocationTargetException cause) {
            throw translate(setter, cause.getTargetException());
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
        } catch (InvocationTargetException cause) {
            throw translate(getter, cause.getTargetException());
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
    public static <E> Class<E> cast(Class<?> type) {
        return (Class<E>) type;
    }

}
