package io.github.up2jakarta.lov.core;

/**
 * Extension of {@link java.util.function.BiFunction} that throws checked exception.
 */
@FunctionalInterface
public interface Processor<K, V, T, X extends Throwable> {
    T get(K key, V value) throws X;
}
