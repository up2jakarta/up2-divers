package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.reflect.*;
import java.util.*;

import static io.github.up2jakarta.csv.core.ext.Beans.getDefaultConstructor;
import static io.github.up2jakarta.csv.core.ext.Beans.newInstance;
import static java.util.Arrays.sort;
import static java.util.Collections.unmodifiableMap;
import static java.util.Comparator.comparingInt;

@SuppressWarnings("unused")
public final class Defaults {

    public static final String[] EMPTY = {};
    private static final Map<Class<?>, Object> CACHE;
    // Default values
    public static boolean DEFAULT_BOOLEAN;
    public static char DEFAULT_CHAR;
    public static byte DEFAULT_BYTE;
    public static short DEFAULT_SHORT;
    public static int DEFAULT_INT;
    public static long DEFAULT_LONG;
    public static float DEFAULT_FLOAT;
    public static double DEFAULT_DOUBLE;

    static {
        final Map<Class<?>, Object> cache = new HashMap<>(8);
        for (final Field field : Defaults.class.getDeclaredFields()) {
            try {
                final Object defaultValue = field.get(null);
                cache.put(field.getType(), field.get(null));
            } catch (Exception ignore) {
            }
        }
        CACHE = unmodifiableMap(cache);
    }

    private Defaults() {
    }

    @SuppressWarnings("unchecked")
    public static Object[] prototype(Executable executable) {
        final Parameter[] parameters = executable.getParameters();
        final Object[] arguments = new Object[parameters.length];
        for (var i = 0; i < parameters.length; i++) {
            final Class<?> type = parameters[i].getType();
            if (type.isPrimitive()) {
                arguments[i] = CACHE.get(type);
            } else if (type.isArray()) {
                arguments[i] = Array.newInstance(type.getComponentType(), 0);
            } else if (Optional.class.isAssignableFrom(type)) {
                arguments[i] = Optional.empty();
            } else if (List.class.isAssignableFrom(type)) {
                arguments[i] = List.of();
            } else if (Set.class.isAssignableFrom(type)) {
                arguments[i] = Set.of();
            } else if (Map.class.isAssignableFrom(type)) {
                arguments[i] = Map.of();
            } else if (Segment.class.isAssignableFrom(type)) {
                try {
                    arguments[i] = prototype((Class<? extends Segment>) type);
                } catch (Exception ignore) {
                }
            }
        }
        return arguments;
    }

    @SuppressWarnings("unchecked")
    public static <S extends Segment> S prototype(Class<S> type) throws BeanException {
        try {
            final Constructor<S> constructor = getDefaultConstructor(type);
            if (type.isRecord()) {
                return newInstance(constructor, prototype(constructor));
            }
            return newInstance(constructor);
        } catch (BeanException ignore) {
        }
        final Constructor<?>[] list = type.getDeclaredConstructors();
        sort(list, comparingInt(Constructor::getParameterCount));
        for (Constructor<?> c : list) {
            Beans.setAccessible(c);
            try {
                return (S) Beans.newInstance(c, Defaults.prototype(c));
            } catch (Exception ignore) {
            }
        }
        throw new BeanException(type, "cannot create new instance");
    }

    public static String[] prototype(String[] prototype, int min) {
        if (prototype.length == 0) {
            return EMPTY;
        }
        var lvi = -1;
        for (var i = 0; i < prototype.length; i++) {
            if (prototype[i] != null) {
                lvi = i;
            }
        }
        if (lvi == -1) {
            return EMPTY;
        }
        if (min > 0) {
            final String[] range = new String[lvi - min + 1];
            System.arraycopy(prototype, min, range, 0, range.length);
            return range;
        }
        return prototype;
    }

}
