package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.lov.CodeList;

/**
 * Contact interface for an input record for {@link io.github.up2jakarta.csv.core.ModeType#FULL} mode,
 * useful for events persistence.
 * <p>
 * The record key is referenced by {@link #getReference()}
 *
 * @param <T> the input segment type
 */
public interface IFullRecord<T extends CodeList<T>> extends IFastRecord<T> {

    /**
     * @return the unique reference
     */
    String getReference();

}
