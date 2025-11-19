package io.github.up2jakarta.csv.api.fct;

import io.github.up2jakarta.csv.data.Segment;

import java.util.List;
import java.util.function.Function;

/**
 * Join accessor of one-to-one relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface ISegment<P extends Segment, T extends Segment> extends Function<P, T> {

    /**
     * Adapts the current accessor to valid {@link IJoin}.
     *
     * @return valid join accessor.
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
