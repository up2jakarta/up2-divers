package io.github.up2jakarta.lov;

/**
 * Parsing function that maps {@link String} inputs to {@link R} outputs.
 *
 * @param <R> the target type
 */
@FunctionalInterface
public interface TypeConverter<R> {

    /**
     * Parses or converts the input value to the target {@link R} type.
     *
     * @param value the input data
     * @return the converted object
     * @throws TypeException if any exception occurred during the parsing
     */
    R parse(String value) throws TypeException;

}
