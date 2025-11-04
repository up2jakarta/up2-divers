package io.github.up2jakarta.csv.api.fct;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Getter accessor of collection relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface CollectionJoin<P, T> extends Function<P, Collection<T>> {

    /**
     * Returns a getter accessor of empty relationship.
     *
     * @param <I> the input type
     * @return a valid relation-ship.
     */
    static <I> CollectionJoin<I, I> empty() {
        return (p) -> List.of();
    }

}
