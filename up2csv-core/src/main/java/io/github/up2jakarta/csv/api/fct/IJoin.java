package io.github.up2jakarta.csv.api.fct;

import io.github.up2jakarta.csv.data.Segment;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Join accessor of collection relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface IJoin<P extends Segment, T extends Segment> extends Function<P, Collection<T>> {

    /**
     * Returns valid {@link IJoin} of empty relationship, useful for root segments.
     *
     * @param <T> the input segment type
     * @return valid join accessor.
     */
    static <T extends Segment> IJoin<T, T> empty() {
        return (p) -> List.of();
    }

}
