package io.github.up2jakarta.lov.core;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Default utility class.
 */
public final class Defaults {

    public static final String[] EMPTY = new String[0];
    private static final Map<Class<?>, Object> PDV = Map.of(
            short.class, (short) 0,
            boolean.class, false,
            char.class, (char) 0,
            byte.class, (byte) 0,
            long.class, (long) 0,
            double.class, 0d,
            float.class, 0f,
            int.class, 0
    );

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
                arguments[i] = PDV.get(type);
            } else if (type.isArray()) {
                arguments[i] = Array.newInstance(type.getComponentType(), 0);
            } else if (Wrapper.class.isAssignableFrom(type)) {
                arguments[i] = new Wrapper<>();
            } else if (Optional.class.isAssignableFrom(type)) {
                arguments[i] = Optional.empty();
            } else if (OptionalInt.class.isAssignableFrom(type)) {
                arguments[i] = OptionalInt.empty();
            } else if (OptionalLong.class.isAssignableFrom(type)) {
                arguments[i] = OptionalLong.empty();
            } else if (OptionalDouble.class.isAssignableFrom(type)) {
                arguments[i] = OptionalDouble.empty();
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
