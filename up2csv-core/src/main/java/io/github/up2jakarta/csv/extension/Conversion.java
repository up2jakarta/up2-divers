package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.PropertyException;

/**
 * Parsing function that is able to convert input data to the target {@link R} type.
 *
 * @param <R> the target type
 */
@FunctionalInterface
public interface Conversion<R> {

    /**
     * Factory method to wrap Conversion with the given error annotation configuration.
     *
     * @param origin the conversion function
     * @param config the error configuration
     * @param <T>    the target type
     * @return wrapped conversion if the config is not null
     */
    static <T> Conversion<T> of(Conversion<T> origin, Error config) {
        if (config != null) {
            return of(origin, config.severity(), config.value());
        }
        return origin;
    }

    /**
     * Factory method to wrap Conversion with the given error annotation configuration.
     *
     * @param origin the conversion function
     * @param type   the error severity
     * @param code   the error code
     * @param <T>    the target type
     * @return wrapped conversion with the given error arguments
     */
    static <T> Conversion<T> of(Conversion<T> origin, SeverityType type, String code) {
        return v -> {
            try {
                return origin.apply(v);
            } catch (RuntimeException error) {
                throw PropertyException.of(type, code, error);
            }
        };
    }

    /**
     * Parsing function, converts the given value to the target type.
     *
     * @param value the input data
     * @return the converted object
     * @throws Exception if any error during conversion
     */
    R apply(String value) throws Exception;

}
