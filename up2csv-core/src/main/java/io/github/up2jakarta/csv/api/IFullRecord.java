package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Referencable;

/**
 * Contact interface for an input record for {@link io.github.up2jakarta.csv.core.ModeType#FULL} mode,
 * useful for error persistence.
 * <p>
 * The record key is referenced by {@link #getReference()}
 *
 * @param <T> the input type definition
 * @param <P> the pivot type
 */
public interface IFullRecord<T extends IType<?, T>, P extends Comparable<P>> extends IFastRecord<T, P>, Referencable<String> {

}
