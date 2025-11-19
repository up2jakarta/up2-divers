package io.github.up2jakarta.csv.api.fct;

import io.github.up2jakarta.csv.data.Segment;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static io.github.up2jakarta.csv.core.ext.Beans.concat;

/**
 * Link accessor of parent-child relationship.
 *
 * @param <P> the parent class
 * @param <T> the child class
 */
@FunctionalInterface
public interface ILink<P extends Segment, T extends Segment> extends BiConsumer<P, T> {

    /**
     * Returns the setter accessor of empty relationship, useful for root segments.
     *
     * @param <T> the input segment type
     * @return valid link accessor.
     */
    static <T extends Segment> ILink<T, T> empty() {
        return (p, c) -> {
        };
    }

    /**
     * Adapts the specified optional link-accessor to valid {@link ILink}.
     *
     * @param <P> the parent class
     * @param <T> the child class
     * @return valid link accessor
     */
    static <P extends Segment, T extends Segment> ILink<P, T> of(BiConsumer<P, Optional<T>> setter) {
        return (p, c) -> setter.accept(p, Optional.of(c));
    }

    /**
     * Adapts the specified join-accessor to valid {@link ILink}.
     *
     * @param <P> the parent class
     * @param <T> the child class
     * @return valid link accessor.
     */
    static <P extends Segment, T extends Segment> ILink<P, T> of(Function<P, Collection<T>> setter) {
        return (p, c) -> setter.apply(p).add(c);
    }

    /**
     * Adapts the specified array join-accessor to valid {@link ILink}.
     *
     * @param <P> the parent class
     * @param <T> the child class
     * @return valid link accessor.
     */
    static <P extends Segment, T extends Segment> ILink<P, T> of(Function<P, T[]> getter, BiConsumer<P, T[]> setter) {
        return (p, c) -> setter.accept(p, concat(getter.apply(p), c));
    }

    /**
     * Adapts the specified map-value join-accessor to valid {@link ILink}.
     *
     * @param <P> the parent class
     * @param <T> the child class
     * @return valid link accessor.
     */
    static <P extends Segment, T extends Segment, K> ILink<P, T> of(Function<P, Map<K, T>> getter, Function<T, K> key) {
        return (p, c) -> getter.apply(p).put(key.apply(c), c);
    }

    /**
     * Adapts the specified map-key join-accessor to valid {@link ILink}.
     *
     * @param <P> the parent class
     * @param <T> the child class
     * @return valid link accessor.
     */
    static <P extends Segment, T extends Segment, V> ILink<P, T> mk(Function<P, Map<T, V>> getter, Function<T, V> val) {
        return (p, c) -> getter.apply(p).put(c, val.apply(c));
    }

}
