package io.github.up2jakarta.lov;

/**
 * Formatting function that is able to convert the values of type {@link R} to the target {@link String} type.
 *
 * @param <R> the target type
 */
@FunctionalInterface
public interface PropertyFormatter<R> {

    /**
     * Formats the specified value to {@link String}.
     *
     * @param value the property value
     * @return the formatted sequence
     * @throws PropertyException if any exception occurred during formating
     */
    String format(R value) throws PropertyException;

}
