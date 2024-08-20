package io.github.up2jakarta.csv.misc;

import java.util.List;

/**
 * List-able interface contract.
 *
 * @param <T> the element type.
 */
public interface Listable<T> {

    /**
     * @return the list of contained element
     */
    List<T> toList();
}
