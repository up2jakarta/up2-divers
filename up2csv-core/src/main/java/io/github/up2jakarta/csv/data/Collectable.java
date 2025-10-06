package io.github.up2jakarta.csv.data;

import java.util.Collection;

/**
 * Collection-able interface contract.
 *
 * @param <E> the element type.
 */
@FunctionalInterface
public interface Collectable<E> {

    /**
     * @return the collection of contained element
     */
    Collection<E> toCollection();

}
