package io.github.up2jakarta.csv.api.fct;

import io.github.up2jakarta.csv.data.Segment;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * Join accessor of array relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface IArray<P extends Segment, T extends Segment> extends Function<P, T[]> {

    /**
     * Adapts the current accessor to valid {@link IJoin}.
     *
     * @return valid join accessor.
     */
    default IJoin<P, T> values() {
        return (p) -> {
            final T[] values = this.apply(p);
            if (values == null) {
                return List.of();
            }
            return asList(values);
        };
    }

}
