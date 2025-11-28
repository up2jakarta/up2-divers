package io.github.up2jakarta.lov.core;

import java.util.List;

/**
 * {@link List} provider interface contract.
 *
 * @param <E> the element type.
 */
@FunctionalInterface
public interface Listable<E> {

    /**
     * @return the list of contained elements
     */
    List<E> toList();

}
