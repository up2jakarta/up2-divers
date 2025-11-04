package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Referencable;

/**
 * Contact interface for an input record for {@link io.github.up2jakarta.csv.core.ModeType#FULL} mode,
 * useful for error persistence.
 * <p>
 * The record key is referenced by {@link Referencable#getReference()}
 *
 * @param <T> the input type definition
 */
public interface IFullRecord<T extends IType<?, T>> extends IFastRecord<T>, Referencable {

}
