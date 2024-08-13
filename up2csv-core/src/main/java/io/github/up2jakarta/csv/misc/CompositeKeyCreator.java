package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.core.EventCreator;
import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRow;

import java.util.function.Supplier;

/**
 * Simple configurable implementation of {@link EventCreator} that separates the error and its keys.
 *
 * @param <R> the row type
 * @param <K> the error key type
 * @param <E> the error type
 */
public final class CompositeKeyCreator<R extends InputRow, K extends InputError.Key<R>, E extends InputError<R, K>> extends EventCreator<R, K, E> {

    private final Supplier<E> errorCreator;
    private final Supplier<K> keyCreator;

    public CompositeKeyCreator(Supplier<E> errorCreator, Supplier<K> keyCreator) {
        this.errorCreator = errorCreator;
        this.keyCreator = keyCreator;
    }

    @Override
    protected E newInstance() {
        final E error = errorCreator.get();
        if (!(error instanceof InputError.Key<?>)) {
            final K key = keyCreator.get();
            error.setKey(key);
        }
        return error;
    }
}
