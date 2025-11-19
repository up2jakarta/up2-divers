package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.lov.CodeList;

/**
 * Contact interface for an input record for {@link io.github.up2jakarta.csv.core.ModeType#FULL} mode,
 * useful for events persistence.
 * <p>
 * The record key is referenced by {@link #getReference()}
 *
 * @param <T> the input segment type
 * @param <P> the input pivot type
 */
public interface IFullRecord<T extends CodeList<T>, P extends Comparable<P>> extends IFastRecord<T, P>, Referencable<String> {

}
