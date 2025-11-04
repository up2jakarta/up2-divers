package io.github.up2jakarta.csv.api;

/**
 * Contact interface for an input record, it is compatible with {@link io.github.up2jakarta.csv.core.ModeType#FAST}.
 *
 * @param <I> the input type implementation
 */
public interface IFastRecord<I extends IType<?, I>> extends IRecord<I> {

    /**
     * @return the pivot aka the business-key that used to aggregate many records
     */
    String getPivot();

}
