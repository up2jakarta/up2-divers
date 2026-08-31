package io.github.up2jakarta.lov.core;

/**
 * Extension of {@link java.util.function.Function} that throws any kind of exception (checked or not).
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 * @param <X> the type of thrown exception.
 */
@FunctionalInterface
public interface Resolver<K, V, X extends Throwable> {
    V get(K key) throws X;
}
