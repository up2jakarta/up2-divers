package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.BeanException;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Up2 configurable {@link io.github.up2jakarta.csv.cfg.Resolver}
 * that converts the input data before setting the destination property.
 *
 * @param <A> the annotation type
 */
public abstract class ConversionResolver<A extends Annotation> {

    /**
     * Get the annotation {@link Error} if present.
     *
     * @param property the java field
     * @return the optional annotation
     */
    public static Optional<Error> getError(Field property) {
        final Error error = property.getAnnotation(Error.class);
        if (error == null) {
            return Optional.ofNullable(property.getType().getAnnotation(Error.class));
        }
        return Optional.of(error);
    }

    /**
     * Configures and returns the Conversion function {@link java.util.function.Function}.
     *
     * @param config   the annotation that activate the resolution
     * @param property the segment property
     * @return the right conversion
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract PropertyConverter<?> forParsing(@NotNull A config, @NotNull Field property, @NotNull Class<?> type) throws BeanException;

    /**
     * Configures and returns the Format function {@link java.util.function.Function}.
     *
     * @param config   the annotation that activate the resolution
     * @param property the segment property
     * @return the right conversion
     */
    public PropertyFormatter<?> forFormatting(@NotNull A config, @NotNull Field property) {
        return Object::toString;
    }

}
