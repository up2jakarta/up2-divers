package io.github.up2jakarta.csv.ext;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.Recordable;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Wrapper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static io.github.up2jakarta.lov.core.Overrides.get;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.copyOf;

/**
 * Utility class for Java beans manipulation.
 */
public final class Beans extends io.github.up2jakarta.lov.core.Beans {

    public static final List<Class<?>> WRAP_TYPES = List.of(
            Optional.class, Wrapper.class, OptionalInt.class, OptionalLong.class, OptionalDouble.class
    );

    private Beans() {
    }

    private static void permittedTypes(Class<?>[] types, Consumer<Class<?>> collector) {
        if (types != null) {
            for (final Class<?> type : types) {
                collector.accept(type);
                permittedTypes(type.getPermittedSubclasses(), collector);
            }
        }
    }

    private static boolean isValidTyping(Field fp, Class<?> ft, boolean mb, Type mt, Class<?> mc) {
        if (fp.getGenericType().equals(mt)) {
            return true;
        }
        final Type type;
        if ((mt instanceof ParameterizedType t) && t.getActualTypeArguments().length == 1) {
            if (t.getRawType() != mc) {
                return false;
            }
            type = t.getActualTypeArguments()[0];
        } else if ((fp.getGenericType() instanceof ParameterizedType t) && t.getActualTypeArguments().length == 1) {
            final Type fc = t.getActualTypeArguments()[0];
            return mb && (t.getRawType() == mc) && ((fc instanceof TypeVariable<?>) || (mt == mc));
        } else {
            type = mt;
        }
        if (mc == Object.class) {
            return mb && (fp.getGenericType() instanceof TypeVariable<?>);
        } else if (type instanceof Class<?> c) {
            return c == ft || c.isAssignableFrom(ft);
        }
        return type instanceof TypeVariable<?>;
    }

    public static void update(Segment target, IRecord<?> source, Consumer<RuntimeException> handler) {
        try {
            //noinspection unchecked
            ((Recordable<IRecord<?>>) target).setRecord(source);
        } catch (RuntimeException cause) {
            handler.accept(cause);
        }
    }

    private static Method findSetter(Field fp, Class<?> type, Class<?> ft) throws BeanException {
        final String fn = fp.getName();
        final String pName = capitalize(fn);
        try {
            return findMethod(type, fp.getDeclaringClass(), "set" + pName, fn, "setter", ft);
        } catch (BeanException ignore) {
            return findMethod(type, fp.getDeclaringClass(), "set" + pName, fn, "setter", fp.getType());
        }
    }

    private static Method findGetter(Field fp, Class<?> type, Class<?> ft) throws BeanException {
        final String fn = fp.getName();
        if (type.isRecord()) {
            return findMethod(type, fp.getDeclaringClass(), fn, fn, "getter");
        }
        final String pName = capitalize(fn);
        if (!WRAP_TYPES.contains(fp.getType()) && (ft == Boolean.class || ft == boolean.class)) {
            try {
                return findMethod(type, fp.getDeclaringClass(), "is" + pName, fn, "getter");
            } catch (BeanException ignore) {
            }
        }
        return findMethod(type, fp.getDeclaringClass(), "get" + pName, fn, "getter");
    }

    public static <T> T[] concat(T[] source, T value) {
        final T[] values = copyOf(source, source.length + 1);
        values[source.length] = value;
        return values;
    }

    public static Stream<String> getPermittedTypes(Class<?>... types) {
        final List<Class<?>> result = new LinkedList<>();
        permittedTypes(types, result::add);
        return result.stream().map(Beans::getClassName);
    }

    public static String getClassName(Class<?> type) {
        return getTypeName(type, '$').insert(0, ".").insert(0, type.getPackageName()).toString();
    }

    public static Optional<Error> error(Field field, Class<?> type) {
        final Error fa = field.getAnnotation(Error.class);
        if (fa == null) {
            return Optional.ofNullable(get(type, Object.class, Error.class));
        }
        return Optional.of(fa);
    }

    public static Class<? extends Segment> segmentType(List<Class<? extends Segment>> stack) {
        Class<? extends Segment> segmentType = stack.getLast();
        for (Class<? extends Segment> superType : stack) {
            if (segmentType.isAssignableFrom(superType)) {
                segmentType = superType;
                break;
            }
        }
        return segmentType;
    }

    public static List<Class<? extends Segment>> cleanStack(List<Class<? extends Segment>> stack) {
        final List<Class<? extends Segment>> result = new LinkedList<>();
        result.addLast(stack.getFirst());
        for (Class<? extends Segment> superType : stack) {
            if (!superType.isAssignableFrom(result.getLast())) {
                result.addLast(superType);
            }
        }
        return result;
    }

    public static void rethrow(List<BeanException> causes) throws BeanException {
        if (!causes.isEmpty()) {
            final BeanException main = causes.getFirst();
            for (final BeanException cause : causes) {
                if (cause != main) {
                    main.addSuppressed(cause);
                }
            }
            throw main;
        }
    }

    public static Method findMethod(Class<?> ft, Class<?> st, String mn, String el, String em, Class<?>... ms) throws BeanException {
        var sc = ft;
        do {
            try {
                final Method m = sc.getDeclaredMethod(mn, ms);
                if (!isStatic(m.getModifiers())) {
                    return m;
                }
            } catch (Exception ignore) {
            }
            sc = sc.getSuperclass();
        } while (sc != null && st.isAssignableFrom(sc));
        throw new BeanException(ft, el, em + " not found");
    }

    public static Method findMethod(Class<?> ft, String mn, String el, String em, Class<?>... ms) throws BeanException {
        return findMethod(ft, Object.class, mn, el, em, ms);
    }

    public static Method findGetter(Class<?> st, Field fp, Class<?> ft) throws BeanException {
        final Method gm = findGetter(fp, st, ft);
        if (isValidTyping(fp, ft, gm.isBridge(), gm.getGenericReturnType(), gm.getReturnType())) {
            return gm;
        }
        throw new BeanException(st, fp.getName(), "invalid getter return type");
    }

    public static Method findSetter(Class<?> st, Field fp, Class<?> ft) throws BeanException {
        final Method sm = findSetter(fp, st, ft);
        final Parameter p = sm.getParameters()[0];
        if (isValidTyping(fp, ft, sm.isBridge(), p.getParameterizedType(), p.getType())) {
            return sm;
        }
        throw new BeanException(st, fp.getName(), "invalid setter parameter type");
    }

    public static AccessException translate(Member source, Throwable cause) {
        if (cause instanceof AccessException ae) {
            final Class<?> type = source.getDeclaringClass();
            final String locator = source.getName();
            if (type == ae.getSource() && locator.equals(ae.getLocator())) {
                return ae;
            }
            return new AccessException(type, locator, ae.getMessage(), ae.getCause());
        }
        return new AccessException(source, cause);
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(MethodHandle handle, Object bean, Member source) throws AccessException {
        try {
            return (V) handle.invoke(bean);
        } catch (Throwable cause) {
            throw translate(source, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(VarHandle handle, Object bean, Member source) throws AccessException {
        try {
            return (V) handle.get(bean);
        } catch (Exception cause) {
            throw new AccessException(source, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(Method getter, Object bean, Member source) throws AccessException {
        try {
            return (V) getter.invoke(bean);
        } catch (InvocationTargetException cause) {
            throw translate(source, cause.getTargetException());
        } catch (Exception cause) {
            throw translate(source, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V getValue(Field field, Object bean, Member source) throws AccessException {
        try {
            return (V) field.get(bean);
        } catch (Exception cause) {
            throw new AccessException(source, cause);
        }
    }

    public static <V> void setValue(MethodHandle handle, Object bean, V value, Member source) throws AccessException {
        try {
            handle.invoke(bean, value);
        } catch (Throwable cause) {
            throw translate(source, cause);
        }
    }

    public static <V> void setValue(VarHandle handle, Object bean, V value, Member source) throws AccessException {
        try {
            handle.set(bean, value);
        } catch (Exception cause) {
            throw new AccessException(source, cause);
        }
    }

    public static <V> void setValue(Method setter, Object bean, V value, Member source) throws AccessException {
        try {
            setter.invoke(bean, value);
        } catch (InvocationTargetException cause) {
            throw translate(source, cause.getTargetException());
        } catch (Exception cause) {
            throw translate(source, cause);
        }
    }

    public static <V> void setValue(Field field, Object bean, V value, Member source) throws AccessException {
        try {
            field.set(bean, value);
        } catch (Exception cause) {
            throw new AccessException(source, cause);
        }
    }

}
