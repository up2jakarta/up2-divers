package io.github.up2jakarta.csv.input;

/**
 * Contact interface for an input record.
 */
public interface InputSegment<T extends InputType<?, T>> {

    /**
     * @return the input type
     */
    T getType();

    /**
     * @return the input data, it could be truncated to avoid performance issues
     */
    String[] getColumns();

}
