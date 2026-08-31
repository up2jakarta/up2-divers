package io.github.up2jakarta.lov.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

/**
 * Override utility class.
 */
public final class Overrides {

    private Overrides() {
    }

    public static boolean equals(String[] path, String[] source, int sourceIndex) {
        if (path.length == source.length - sourceIndex) {
            for (var i = 0; i < path.length; i++) {
                if (!path[i].equals(source[sourceIndex + i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static <A extends Annotation> A get(Class<?> beanType, Class<?> markerType, Class<A> type) {
        while (beanType != markerType && markerType.isAssignableFrom(beanType)) {
            final A result = beanType.getAnnotation(type);
            if (result != null) {
                return result;
            }
            beanType = beanType.getSuperclass();
        }
        return null;
    }

    public static <A extends Annotation> A get(Class<A> t, AnnotatedElement e, Function<A, String[]> m) throws BeanException {
        final List<A> overrides = stream(e.getAnnotationsByType(t))
                .filter(o -> m.apply(o).length == 0)
                .toList();
        if (overrides.isEmpty()) {
            return null;
        }
        if (overrides.size() == 1) {
            return overrides.getFirst();
        }
        throw BeanException.of(e, "multiple @" + getTypeName(t) + "(path = {})");
    }

    public static <A extends Annotation> A get(Class<A> t, Map<Path, A> ps, Field f, Function<A, String[]> m) throws BeanException {
        return ps.entrySet().stream()
                .filter(e -> e.getKey().equals(f))
                .peek(e -> e.getKey().used.accept(true))
                .map(Entry::getValue)
                .findFirst()
                .orElse(get(t, f, m));
    }

    public static <O extends Annotation, A extends Annotation> A get(Class<A> t, Map<Path, O> ps, Field f, Function<O, A> m, Predicate<A> p) {
        final Optional<Entry<Path, O>> override = ps.entrySet().stream()
                .filter(e -> e.getKey().equals(f))
                .peek(e -> e.getKey().used.accept(true))
                .findFirst();
        if (override.isPresent()) {
            final A result = m.apply(override.get().getValue());
            return p.test(result) ? result : null;
        }
        return f.getAnnotation(t);
    }

    public static <A extends Annotation> void add(Path path, A override, String pn, BiConsumer<Path, A> c) {
        final String[] paths = path.path;
        if (paths.length > 1 && paths[0].equals(pn)) {
            c.accept(path.next(), override);
        }
    }

    public static <A extends Annotation> void add(Class<A> t, AnnotatedElement e, BiConsumer<Path, A> c, Function<A, String[]> m) throws BeanException {
        final A[] overrides = e.getAnnotationsByType(t);
        for (int i = 0; i < overrides.length; i++) {
            final A override = overrides[i];
            final Path source = new Path(e, m.apply(override));
            for (int j = i + 1; j < overrides.length; j++) {
                if (equals(m.apply(overrides[j]), source.path, 0)) {
                    throw BeanException.of(e, "multiple @" + getTypeName(t) + "(path = " + source + ")");
                }
            }
            c.accept(source, override);
        }
    }

    public static List<BeanException> check(Set<Path> overrides, Class<? extends Annotation> type, Filter cf) {
        final String format = "@" + getTypeName(type) + "(path = %s) is never used";
        return overrides.stream()
                .filter(p -> p.mine)
                .filter(p -> !p.used.or(false))
                .filter(p -> cf.test(p.src, p.path.length))
                .map(p -> BeanException.of(p.src, String.format(format, p)))
                .toList();
    }

    /**
     * Override Path Filter
     */
    @FunctionalInterface
    public interface Filter extends BiPredicate<AnnotatedElement, Integer> {
        Filter ALL = (e, s) -> true;

        default Filter and(Filter that) {
            return (e, s) -> this.test(e, s) && that.test(e, s);
        }
    }

    /**
     * Override Path Element
     */
    public static final class Path {
        private final Wrapper<Boolean> used;
        private final AnnotatedElement src;
        private final String[] path;
        private final boolean mine;

        private Path(AnnotatedElement src, String... path) {
            this.used = new Wrapper<>(false);
            this.src = src;
            this.path = path;
            this.mine = true;
        }

        private Path(Path source) {
            this.mine = false;
            this.src = source.src;
            this.used = source.used;
            this.path = copyOfRange(source.path, 1, source.path.length);
        }

        public Path next() {
            return new Path(this);
        }

        public boolean equals(Field property) {
            if (path.length != 1) {
                return false;
            }
            return path[0].equals(property.getName());
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Path that)) {
                return false;
            }
            return Overrides.equals(path, that.path, 0);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(path);
        }

        @Override
        public String toString() {
            if (path.length == 0) {
                return "{}";
            }
            return "{\"" + String.join("\", \"", path) + "\"}";
        }

    }
}
