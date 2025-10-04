package io.github.up2jakarta.csv.api.fct;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Getter accessor of array relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface ArrayJoin<P, T> extends Function<P, T[]> {

    /**
     * Adapts the current accessor to a valid collection relationship.
     *
     * @return a valid relation-ship.
     */
    default IJoin<P, T> values() {
        return (p) -> {
            final T[] values = this.apply(p);
            if (values == null) {
                return List.of();
            }
            return Arrays.asList(values);
        };
    }

}
