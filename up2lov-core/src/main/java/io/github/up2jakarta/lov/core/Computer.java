package io.github.up2jakarta.lov.core;

/**
 * Extension of {@link java.util.function.Supplier} that throws any kind of exception (checked or not).
 *
 * @param <T> the type of the key
 * @param <X> the type of result computed by this computer
 */
@FunctionalInterface
public interface Computer<T, X extends Throwable> {
    T get() throws X;
}
