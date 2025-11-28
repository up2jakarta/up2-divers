package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static io.github.up2jakarta.lov.core.Localizable.CONSTRUCTOR;
import static io.github.up2jakarta.lov.core.Overrides.get;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.stream;

public abstract class Beans extends io.github.up2jakarta.lov.core.Beans {

    private static Method checkGetter(Method fg, Field fp, Class<?> ft) throws BeanException {
        final Class<?> rt = fg.getReturnType();
        if (!(rt == ft || rt == fp.getType() || fg.getGenericReturnType().equals(fp.getGenericType()))) {
            throw new BeanException(fp, "invalid return type");
        }
        return fg;
    }

    private static void permittedTypes(Class<?>[] types, Consumer<Class<?>> collector) {
        if (types != null) {
            for (final Class<?> type : types) {
                collector.accept(type);
                permittedTypes(type.getPermittedSubclasses(), collector);
            }
        }
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

    public static Optional<Error> error(Field property, Class<?> type) {
        final Error config = property.getAnnotation(Error.class);
        if (config == null) {
            return Optional.ofNullable(get(type, Object.class, Error.class));
        }
        return Optional.of(config);
    }

    public static Class<? extends Segment> segmentType(Stack<Class<? extends Segment>> stack) {
        Class<? extends Segment> segmentType = stack.peek();
        for (Class<? extends Segment> superType : stack) {
            if (segmentType.isAssignableFrom(superType)) {
                segmentType = superType;
                break;
            }
        }
        return segmentType;
    }

    public static Stack<Class<? extends Segment>> cleanStack(Stack<Class<? extends Segment>> stack) {
        final Stack<Class<? extends Segment>> result = new Stack<>();
        result.push(stack.getFirst());
        for (Class<? extends Segment> superType : stack) {
            if (!superType.isAssignableFrom(result.peek())) {
                result.push(superType);
            }
        }
        return result;
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

    public static Method getAccessibleSetter(Class<?> type, Field fp, Class<?> ft) throws BeanException {
        final String fn = fp.getName();
        final String pName = capitalize(fn);
        try {
            return getMethod(type, "set" + pName, fn, "setter", ft);
        } catch (BeanException ignore) {
            return getMethod(type, "set" + pName, fn, "setter", fp.getType());
        }
    }

    public static Method getAccessibleGetter(Class<?> type, Field fp, Class<?> ft) throws BeanException {
        final String fn = fp.getName();
        if (type.isRecord()) {
            return getMethod(type, fn, fn, "getter");
        }
        final String pName = capitalize(fn);
        if (ft == Boolean.class || ft == boolean.class) {
            try {
                return checkGetter(getMethod(type, "is" + pName, fn, "getter"), fp, ft);
            } catch (BeanException ignore) {
            }
        }
        return checkGetter(getMethod(type, "get" + pName, fn, "getter"), fp, ft);
    }

    public static <A extends AccessibleObject & Member> A setAccessible(A source) {
        try {
            source.setAccessible(true);
            return source;
        } catch (Exception cause) {
            final String locator = (source instanceof Constructor<?>) ? CONSTRUCTOR : source.getName();
            throw new AccessException(source.getDeclaringClass(), locator, cause.getMessage());
        }
    }

    public static boolean isInnerType(Class<?> type) {
        return type.getEnclosingClass() != null && !isStatic(type.getModifiers());
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
            throw new BeanException(type, CONSTRUCTOR, cause.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(BeanContext context, Class<?> type, String name) throws BeanException {
        try {
            if (name.isEmpty()) {
                return (T) context.getBean(type);
            }
            return (T) context.getBean(type, name);
        } catch (Exception e) {
            throw new BeanException(type, "qualified bean must be found");
        }
    }

}
