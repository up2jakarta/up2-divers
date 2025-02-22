package io.github.up2jakarta.csv.input;

/**
 * Contact interface for an input record.
 */
public interface InputSegment {

    /**
     * @return the input type
     */
    InputType<?> getType();

    /**
     * @return the input data, it could be truncated to avoid performance issues
     */
    String[] getColumns();

}
