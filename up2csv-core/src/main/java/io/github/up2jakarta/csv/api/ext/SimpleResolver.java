package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Simple base implementation of {@link TypeResolver} activated by marker annotation without extra-configuration.
 *
 * @param <C> the annotation type
 * @param <T> the property super-type
 */
public abstract class SimpleResolver<T, C extends Annotation> implements TypeResolver<T, C> {

    @Override
    public final TypeAdapter<? extends T> resolve(Field pf, Class<T> pt, C pc) throws BeanException {
        return this.resolve(pf, pt);
    }

    /**
     * Configures and returns the {@link TypeAdapter} related to the specified type.
     *
     * @param pf the segment property
     * @param pt the computed property type
     * @return the right property adapter
     */
    protected abstract TypeAdapter<? extends T> resolve(Field pf, Class<T> pt) throws BeanException;
}
