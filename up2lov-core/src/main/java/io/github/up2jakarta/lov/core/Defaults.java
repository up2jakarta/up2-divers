package io.github.up2jakarta.lov.core;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class Defaults {

    public static final String[] EMPTY = {};
    private static final Map<Class<?>, Object> CACHE;
    // Default values
    public static boolean DEFAULT_BOOLEAN;
    public static double DEFAULT_DOUBLE;
    public static float DEFAULT_FLOAT;
    public static short DEFAULT_SHORT;
    public static char DEFAULT_CHAR;
    public static byte DEFAULT_BYTE;
    public static long DEFAULT_LONG;
    public static int DEFAULT_INT;

    static {
        CACHE = Map.of(
                boolean.class, DEFAULT_BOOLEAN,
                double.class, DEFAULT_DOUBLE,
                float.class, DEFAULT_FLOAT,
                short.class, DEFAULT_SHORT,
                char.class, DEFAULT_CHAR,
                byte.class, DEFAULT_BYTE,
                long.class, DEFAULT_LONG,
                int.class, DEFAULT_INT
        );
    }

    private Defaults() {
    }

    public static Class<?> wrap(Class<?> type) {
        if (type.isPrimitive()) {
            if (type == int.class) {
                return Integer.class;
            }
            if (type == long.class) {
                return Long.class;
            }
            if (type == boolean.class) {
                return Boolean.class;
            }
            if (type == byte.class) {
                return Byte.class;
            }
            if (type == char.class) {
                return Character.class;
            }
            if (type == float.class) {
                return Float.class;
            }
            if (type == double.class) {
                return Double.class;
            }
            if (type == short.class) {
                return Short.class;
            }
        }
        return type;
    }

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
            }
        }
        return arguments;
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
