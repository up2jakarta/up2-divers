package io.github.up2jakarta.csv.api.fct;

import io.github.up2jakarta.csv.data.Segment;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Join accessor of map-key relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface IMapKey<P extends Segment, T extends Segment> extends Function<P, Map<T, ?>> {

    /**
     * Adapts the current accessor to valid {@link IJoin}.
     *
     * @return valid join accessor.
     */
    default IJoin<P, T> keys() {
        return p -> {
            final Map<T, ?> value = this.apply(p);
            if (value == null) {
                return List.of();
            }
            return value.keySet();
        };
    }

}
