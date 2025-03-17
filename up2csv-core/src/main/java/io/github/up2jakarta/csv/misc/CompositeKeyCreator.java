package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.core.EventCreator;
import io.github.up2jakarta.csv.extension.DataType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;

import java.util.function.Supplier;

/**
 * Simple configurable implementation of {@link EventCreator} that separates the error and its keys.
 *
 * @param <R> the row type
 * @param <K> the error key type
 * @param <E> the error type
 */
public class CompositeKeyCreator<R extends InputSegment<?>, K extends InputError.Key<R>, D extends DataType<D>, E extends InputError<R, K, D>> extends EventCreator<R, K, D, E> {

    private final Supplier<E> errorCreator;
    private final Supplier<K> keyCreator;

    /**
     * Constructor with error and its key creators.
     *
     * @param errorCreator the error creator
     * @param keyCreator   the error key creator
     */
    public CompositeKeyCreator(Supplier<E> errorCreator, Supplier<K> keyCreator) {
        this.errorCreator = errorCreator;
        this.keyCreator = keyCreator;
    }

    @Override
    protected final E newInstance() {
        final E error = errorCreator.get();
        if (!(error instanceof InputError.Key<?>)) {
            final K key = keyCreator.get();
            error.setKey(key);
        }
        return error;
    }
}
