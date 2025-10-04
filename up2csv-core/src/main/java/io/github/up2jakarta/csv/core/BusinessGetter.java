package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.ParentId;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.misc.BeanException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.misc.Beans.*;

@FunctionalInterface
interface BusinessGetter {

    BusinessGetter NULL = (b, t) -> UNDEFINED;

    private static BusinessGetter of(Property<?, ?>... path) {
        if (path.length == 1) {
            final Method getter = path[0].getter;
            return (b, t) -> getValue(b, getter);
        }
        final List<Method> getters = new ArrayList<>(path.length);
        for (final Property<?, ?> p : path) {
            getters.add(p.getter);
        }
        return (b, t) -> {
            Object result = b;
            for (final Method getter : getters) {
                result = getValue(result, getter);
                if (result == null) {
                    return null;
                }
            }
            return result;
        };
    }

    private static <A extends Annotation> void walk(
            List<? extends Property<?, ?>> properties, Class<A> annotationType,
            BiConsumer<A, Property<?, ?>[]> collector, Property<?, ?>... stack
    ) throws BeanException {
        for (final Property<?, ?> property : properties) {
            final Field field = property.field;
            final A annotation = field.getAnnotation(annotationType);
            if (property instanceof BeanProperty<?, ?> fp) {
                if (annotation != null) {
                    throw new BeanException(field, "must not be annotated with @" + annotationType.getSimpleName());
                }
                walk(fp.toList(), annotationType, collector, concat(stack, property));
            } else if (annotation != null) {
                collector.accept(annotation, concat(stack, property));
            }
        }
    }

    static <A extends Annotation> BusinessGetter find(
            Class<? extends Segment> type, List<? extends Property<?, ?>> properties, Class<A> annotationType
    ) throws BeanException {
        final Map<A, Property<?, ?>[]> found = new LinkedHashMap<>();
        walk(properties, annotationType, found::put);
        if (found.isEmpty()) {
            return BusinessGetter.NULL;
        } else if (found.size() == 1) {
            final Entry<A, Property<?, ?>[]> first = found.entrySet().iterator().next();
            final BusinessGetter result = of(first.getValue());

            if (first.getKey() instanceof ParentId pid) {
                if (pid.value().length == 0) {
                    return BusinessGetter.NULL;
                }
                for (final Class<? extends Segment> support : pid.value()) {
                    if (support == Segment.class) {
                        return result;
                    }
                }
                return (b, p) -> {
                    for (final Class<? extends Segment> support : pid.value()) {
                        if (support.isAssignableFrom(p)) {
                            return result.get(b, p);
                        }
                    }
                    return UNDEFINED;
                };
            }
            return result;
        }
        throw new BeanException(type, "multiple @" + annotationType.getSimpleName() + " are found");
    }

    Object get(Segment bean, Class<? extends Segment> parentType) throws BeanException;

}
