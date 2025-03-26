package io.github.up2jakarta.csv.misc;

import java.util.Collection;
import java.util.List;

/**
 * List-able interface contract.
 *
 * @param <E> the element type.
 */
public interface Listable<E> {

    /**
     * @return the list of contained element
     */
    List<E> toList();

    /**
     * Pushes the list of collected elements to the given <code>target</code> collection.
     *
     * @param target the copy of elements
     */
    default void addTo(Collection<E> target) {
    }

}
