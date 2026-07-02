package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.Segment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Simple base implementation of {@link TypeExtension} with simple configuration annotation that presents on
 * property only and doesn't need extra lookup or checking.
 *
 * @param <C> the annotation type
 * @param <T> the property super-type
 */
public abstract class SimpleExtension<T, C extends Annotation> implements TypeExtension<T, C> {

    private final Class<C> type;

    protected SimpleExtension(Class<C> type) {
        this.type = type;
    }

    @Override
    public final Optional<C> resolve(Class<? extends Segment> st, Field pf, Class<?> pt, Field... ps) {
        return Optional.ofNullable(pf.getAnnotation(type));
    }

}
