package io.github.up2jakarta.lov.core;

/**
 * Extension of {@link java.util.function.Function} that throws checked exception.
 */
@FunctionalInterface
public interface Resolver<K, V, X extends Throwable> {
    V get(K key) throws X;
}
