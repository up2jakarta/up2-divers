package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Separable;

/**
 * Contact interface for an input record.
 *
 * @param <T> the segment type definition
 */
public interface IRecord<T extends IType<?, T>> extends Separable {

    /**
     * @return the input type
     */
    T getType();

    /**
     * @return the input data, it could be truncated to avoid performance issues
     */
    String[] getColumns();

}
