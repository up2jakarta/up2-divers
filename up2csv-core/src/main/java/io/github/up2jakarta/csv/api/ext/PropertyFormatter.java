package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Formatting function that is able to convert the property value of type {@link R} to the target {@link String} type.
 *
 * @param <R> the target type
 */
@FunctionalInterface
public interface PropertyFormatter<R> {

    /**
     * Factory method to wrap Format function with the given error annotation configuration.
     *
     * @param origin the format function
     * @param config the error configuration
     * @param <T>    the target type
     * @return wrapped format if the config is not null
     */
    static <T> PropertyFormatter<T> of(PropertyFormatter<T> origin, Error config) {
        if (config != null) {
            return of(origin, config.severity(), config.value());
        }
        return origin;
    }

    /**
     * Factory method to wrap Format function with the given error annotation configuration.
     *
     * @param origin the format function
     * @param type   the error severity
     * @param code   the error code
     * @param <T>    the target type
     * @return wrapped format with the given error arguments
     */
    static <T> PropertyFormatter<T> of(PropertyFormatter<T> origin, SeverityType type, String code) {
        return v -> {
            try {
                return origin.apply(v);
            } catch (RuntimeException error) {
                throw PropertyException.of(type, code, error);
            }
        };
    }

    /**
     * Formatting function, formats the given value to the {@link String} type.
     *
     * @param value the property value
     * @return the formatted sequence
     * @throws PropertyException if any error during format
     */
    String apply(R value) throws PropertyException;

}
