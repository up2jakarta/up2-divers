package io.github.up2jakarta.lov;

/**
 * Parsing function that is able to convert input data to the target {@link R} type.
 *
 * @param <R> the target type
 */
@FunctionalInterface
public interface PropertyConverter<R> {

    /**
     * Parses or converts the input value to the target {@link R} type.
     *
     * @param value the input data
     * @return the converted object
     * @throws PropertyException if any exception occurred during the parsing
     */
    R parse(String value) throws PropertyException;

}
