package io.github.up2jakarta.csv.api.fct;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Getter accessor of map-key relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface MapKeyJoin<P, T> extends Function<P, Map<T, ?>> {

    /**
     * Adapts the current accessor to a valid collection relationship.
     *
     * @return a valid relation-ship.
     */
    default IJoin<P, T> keys() {
        return (p) -> {
            final Map<T, ?> value = this.apply(p);
            if (value == null) {
                return List.of();
            }
            return value.keySet();
        };
    }

}
