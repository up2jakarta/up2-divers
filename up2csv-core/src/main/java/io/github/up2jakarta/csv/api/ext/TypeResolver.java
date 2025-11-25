package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanAware;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Up2J configurable {@link io.github.up2jakarta.csv.cfg.Resolver}
 * that converts the input data before setting the destination property.
 *
 * @param <A> the annotation type
 */
public abstract class TypeResolver<A extends Annotation> extends BeanAware {

    /**
     * Configures and returns the {@link TypeAdapter} related to the specified type.
     *
     * @param config   the annotation that activate the resolution
     * @param property the segment property
     * @param type     the computed property type
     * @return the right property adapter
     */
    public abstract TypeAdapter<?> resolve(Field property, Class<?> type, A config) throws BeanException;

}
