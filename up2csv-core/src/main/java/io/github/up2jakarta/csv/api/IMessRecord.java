package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.lov.CodeList;

/**
 * Contact interface for an input record, it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#MESS}.
 *
 * @param <I> the input segment type
 */
public interface IMessRecord<I extends CodeList<I>> extends IRecord<I> {

    /**
     * @return the pivot aka the business-key that used to aggregate many records
     */
    String getPivot();

}
