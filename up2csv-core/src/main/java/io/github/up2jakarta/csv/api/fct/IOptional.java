package io.github.up2jakarta.csv.api.fct;

import io.github.up2jakarta.csv.data.Segment;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Join accessor of {@link Optional} one-to-one relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface IOptional<P extends Segment, T extends Segment> extends Function<P, Optional<T>> {

    /**
     * Adapts the current accessor to valid {@link IJoin}.
     *
     * @return valid join accessor.
     */
    default IJoin<P, T> many() {
        return (p) -> this.apply(p).map(List::of).orElseGet(List::of);
    }

}
