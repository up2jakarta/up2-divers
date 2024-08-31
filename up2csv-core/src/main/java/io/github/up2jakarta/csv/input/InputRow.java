package io.github.up2jakarta.csv.input;

/**
 * Contact interface for an input row.
 */
public interface InputRow {

    /**
     * @return the input type
     */
    InputType<?> getType();

    /**
     * @return the input data, it could be truncated to avoid performance issues
     */
    String[] getColumns();

}
