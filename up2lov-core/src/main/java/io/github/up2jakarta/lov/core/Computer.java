package io.github.up2jakarta.lov.core;

/**
 * Extension of {@link java.util.function.Supplier} that throws checked exception.
 */
@FunctionalInterface
public interface Computer<T, X extends Throwable> {
    T get() throws X;
}
