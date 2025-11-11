package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.core.AccessException;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.DefaultProvider;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.up2jakarta.csv.core.ext.Beans.getDefaultConstructor;
import static io.github.up2jakarta.csv.core.ext.Beans.newInstance;
import static java.util.Arrays.sort;
import static java.util.Comparator.comparingInt;

@SuppressWarnings("unchecked")
public final class Defaults {

    public static final String[] EMPTY = {};
    private static final Map<Class<?>, Object> CACHE_DEFAULTS;
    private static final Map<Class<? extends Segment>, Segment> CACHE_SEGMENTS = new ConcurrentHashMap<>();
    private static final Map<Class<? extends CodeList<?>>, CodeList<?>> CACHE_CONSTANTS = new ConcurrentHashMap<>();
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
        CACHE_DEFAULTS = Map.of(
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

    private static <S extends Segment> S any(Class<S> type) {
        try {
            final Constructor<S> constructor = getDefaultConstructor(type);
            if (type.isRecord()) {
                return newInstance(constructor, prototype(constructor));
            }
            return newInstance(constructor);
        } catch (Exception ignore) {
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
        throw new AccessException(type, "new", "@Fragment[nullable] must be enabled because cannot create prototype");
    }

    public static Object[] prototype(Executable executable) {
        final Parameter[] parameters = executable.getParameters();
        final Object[] arguments = new Object[parameters.length];
        for (var i = 0; i < parameters.length; i++) {
            final Class<?> type = parameters[i].getType();
            if (type.isPrimitive()) {
                arguments[i] = CACHE_DEFAULTS.get(type);
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
                arguments[i] = CACHE_SEGMENTS.get(type);
            } else if (CodeList.class.isAssignableFrom(type)) {
                final Class<? extends CodeList<?>> clType = (Class<CodeList<?>>) type;
                arguments[i] = CACHE_CONSTANTS.computeIfAbsent(clType, DefaultProvider::any);
            }
        }
        return arguments;
    }

    public static <S extends Segment> S prototype(Class<S> type) {
        return (S) CACHE_SEGMENTS.computeIfAbsent(type, Defaults::any);
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
