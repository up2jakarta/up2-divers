package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.up2jakarta.csv.core.BeanException.of;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public final class Path {

    private final String[] path;

    private Path(String... path) {
        this.path = path;
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

    public static <A extends Annotation> A getOverride(Class<? extends Segment> bean, Class<A> type) {
        while (bean != Segment.class && Segment.class.isAssignableFrom(bean)) {
            final A result = bean.getAnnotation(type);
            if (result != null) {
                return result;
            }
            //noinspection unchecked
            bean = (Class<? extends Segment>) bean.getSuperclass();
        }
        return null;
    }

    public static <A extends Annotation> A getOverride(
            Class<A> type, AnnotatedElement origin, Function<A, String[]> mapper
    ) throws BeanException {
        final List<A> overrides = stream(origin.getAnnotationsByType(type))
                .filter(o -> mapper.apply(o).length == 0)
                .toList();
        if (overrides.isEmpty()) {
            return null;
        }
        if (overrides.size() == 1) {
            return overrides.getFirst();
        }
        throw of(origin, "multiple @" + type.getSimpleName() + "(path = {})");
    }

    public static <A extends Annotation> A getOverride(
            Class<A> type, Map<Path, A> overrides, Field field, Function<A, String[]> mapper
    ) throws BeanException {
        return overrides.entrySet().stream()
                .filter(e -> e.getKey().equals(field))
                .map(Entry::getValue)
                .findAny()
                .orElse(getOverride(type, field, mapper));
    }

    public static <O extends Annotation, A extends Annotation> A getOverride(
            Class<A> type, Map<Path, O> overrides, Field field, Function<O, A> mapper, Predicate<A> exclude
    ) {
        final Optional<O> override = overrides.entrySet().stream()
                .filter(e -> e.getKey().equals(field))
                .map(Entry::getValue)
                .findAny();
        if (override.isPresent()) {
            final A result = mapper.apply(override.get());
            return exclude.test(result) ? result : null;
        }
        return field.getAnnotation(type);
    }

    public static <A extends Annotation> void addOverride(
            Path path, A override, String property, BiConsumer<Path, A> collector
    ) {
        final String[] paths = path.path;
        if (paths.length > 0 && paths[0].equals(property)) {
            if (paths.length == 1) {
                collector.accept(path, override);
            } else {
                collector.accept(path.next(), override);
            }
        }
    }

    public static <A extends Annotation> void addOverride(
            Class<A> type, AnnotatedElement origin, BiConsumer<Path, A> collector, Function<A, String[]> mapper
    ) throws BeanException {
        final A[] overrides = origin.getAnnotationsByType(type);
        for (int i = 0; i < overrides.length; i++) {
            final A override = overrides[i];
            final Path source = new Path(mapper.apply(override));
            for (int j = i + 1; j < overrides.length; j++) {
                if (equals(mapper.apply(overrides[j]), source.path, 0)) {
                    throw of(origin, "multiple @" + type.getSimpleName() + "(path = " + source + ")");
                }
            }
            collector.accept(source, override);
        }
    }

    public boolean equals(Field property) {
        if (path.length != 1) {
            return false;
        }
        return path[0].equals(property.getName());
    }

    public Path next() {
        return new Path(copyOfRange(path, 1, path.length));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Path that)) {
            return false;
        }
        return equals(path, that.path, 0);
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
