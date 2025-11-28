package io.github.up2jakarta.lov.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

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
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(get(t, f, m));
    }

    public static <O extends Annotation, A extends Annotation> A get(Class<A> t, Map<Path, O> ps, Field f, Function<O, A> m, Predicate<A> p) {
        final Optional<O> override = ps.entrySet().stream()
                .filter(e -> e.getKey().equals(f))
                .map(Map.Entry::getValue)
                .findAny();
        if (override.isPresent()) {
            final A result = m.apply(override.get());
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
            final Path source = new Path(m.apply(override));
            for (int j = i + 1; j < overrides.length; j++) {
                if (equals(m.apply(overrides[j]), source.path, 0)) {
                    throw BeanException.of(e, "multiple @" + getTypeName(t) + "(path = " + source + ")");
                }
            }
            c.accept(source, override);
        }
    }

    public static final class Path {

        private final String[] path;

        private Path(String... path) {
            this.path = path;
        }

        public Path next() {
            return new Path(copyOfRange(path, 1, path.length));
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
