package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Segment;

/**
 * Contact interface for an input record, it is compatible with {@link io.github.up2jakarta.csv.core.ModeType#UNIT}.
 *
 * @param <I> the input type implementation
 */
public interface IRecord<I extends IType<?, I>> extends Segment {

    /**
     * @return the input type
     */
    I getType();

    /**
     * @return the input data, it could be truncated to avoid performance issues
     */
    String[] getColumns();

}
