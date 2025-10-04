package io.github.up2jakarta.csv.api;

/**
 * Contact interface for an input record.
 */
public interface IRecord<T extends IType<?, T>> {

    /**
     * @return the input type
     */
    T getType();

    /**
     * @return the input data, it could be truncated to avoid performance issues
     */
    String[] getColumns();

}
