package io.github.up2jakarta.csv.api.fct;

import java.util.List;
import java.util.function.Function;

/**
 * Getter accessor of one-to-one relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface SingleJoin<P, T> extends Function<P, T> {

    /**
     * Adapts the current accessor to a valid collection relationship.
     *
     * @return a valid relation-ship.
     */
    default IJoin<P, T> many() {
        return (p) -> {
            final T value = this.apply(p);
            if (value == null) {
                return List.of();
            }
            return List.of(value);
        };
    }

}
