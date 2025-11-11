package io.github.up2jakarta.csv.api;

/**
 * Contact interface for an input record, it is compatible with {@link io.github.up2jakarta.csv.core.ModeType#FAST}.
 *
 * @param <I> the input type definition
 * @param <P> the pivot type
 */
public interface IFastRecord<I extends IType<?, I>, P extends Comparable<P>> extends IRecord<I> {

    /**
     * @return the pivot aka the business-key that used to aggregate many records
     */
    P getPivot();

}
