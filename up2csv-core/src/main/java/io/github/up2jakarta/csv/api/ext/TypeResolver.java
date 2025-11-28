package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Up2J configurable {@link io.github.up2jakarta.csv.cfg.Resolver}
 * that converts the input data before setting the destination property.
 *
 * @param <C> the annotation type
 * @param <T> the property super-type
 */
public interface TypeResolver<T, C extends Annotation> {

    /**
     * Configures and returns the {@link TypeAdapter} related to the specified type.
     * <p>
     * Up2J guaranties that the annotation is presents and the type matches with {@link T}.
     *
     * @param pc the annotation that activate the resolution
     * @param pf the segment property
     * @param pt the computed property type
     * @return the right property adapter
     */
    TypeAdapter<? extends T> resolve(Field pf, Class<T> pt, C pc) throws BeanException;

}
