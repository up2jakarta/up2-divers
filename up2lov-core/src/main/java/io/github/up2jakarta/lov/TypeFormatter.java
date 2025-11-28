package io.github.up2jakarta.lov;

/**
 * Formatting function that maps {@link R} inputs to {@link String} outputs.
 *
 * @param <R> the target type
 */
@FunctionalInterface
public interface TypeFormatter<R> {

    /**
     * Formats the specified value to {@link String}.
     *
     * @param value the property value
     * @return the formatted sequence
     * @throws TypeException if any exception occurred during formating
     */
    String format(R value) throws TypeException;

}
