package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Separable;

/**
 * Contact interface for an input record.
 *
 * @param <I> the input type implementation
 */
public interface IRecord<I extends IType<?, I>> extends Separable {

    /**
     * @return the input type
     */
    I getType();

    /**
     * @return the input data, it could be truncated to avoid performance issues
     */
    String[] getColumns();

}
