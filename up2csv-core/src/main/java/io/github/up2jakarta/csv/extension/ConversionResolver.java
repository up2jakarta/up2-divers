package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.exception.BeanException;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Up2 configurable {@link io.github.up2jakarta.csv.annotation.Resolver}
 * that converts the input data before setting the destination property.
 *
 * @param <A> the annotation type
 */
public abstract class ConversionResolver<A extends Annotation> {

    /**
     * Configures and returns the Conversion {@link java.util.function.Function}.
     *
     * @param config   the annotation that activate the resolution
     * @param property the segment property
     * @return the right conversion
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Conversion<?> resolve(@NotNull A config, @NotNull Field property) throws BeanException;

}
