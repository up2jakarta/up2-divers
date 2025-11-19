package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.lov.CodeList;

/**
 * Contact interface for an input record, it is compatible with {@link io.github.up2jakarta.csv.core.ModeType#FAST}.
 *
 * @param <I> the input segment type
 * @param <P> the input pivot type
 */
public interface IFastRecord<I extends CodeList<I>, P extends Comparable<P>> extends IRecord<I> {

    /**
     * @return the pivot aka the business-key that used to aggregate many records
     */
    P getPivot();

}
