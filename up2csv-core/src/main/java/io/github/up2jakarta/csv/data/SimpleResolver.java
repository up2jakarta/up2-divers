package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.TermResolver;
import io.github.up2jakarta.lov.core.Overrides;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Simple business resolver based on declared annotation .
 *
 * @param <A> the annotation type
 * @param <D> the business term type
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 */
public final class SimpleResolver<A extends Annotation, D extends ITerm<D>> extends TermResolver<D> {

    private final Class<A> annotationType;
    private final Function<A, D> adapter;

    public SimpleResolver(Class<D> type, Class<A> annotationType, Function<A, D> adapter) {
        super(type);
        this.annotationType = annotationType;
        this.adapter = adapter;
    }

    @Override
    public Optional<? extends D> get(List<Class<? extends Segment>> stack, Field[] path, Field field) {
        A config = field.getAnnotation(annotationType);
        if (config != null) {
            return Optional.ofNullable(adapter.apply(config));
        }
        for (int i = path.length - 1; i >= 0; i--) {
            if ((config = path[i].getAnnotation(annotationType)) != null) {
                return Optional.ofNullable(adapter.apply(config));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<? extends D> get(Optional<Field> field, Class<? extends Segment> type) {
        A config = null;
        if (field.isPresent()) {
            config = field.get().getAnnotation(this.annotationType);
        }
        if (config == null) {
            config = Overrides.get(type, Segment.class, this.annotationType);
        }
        if (config != null) {
            return Optional.ofNullable(adapter.apply(config));
        }
        return Optional.empty();
    }


}
