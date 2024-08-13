package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.core.EventCreator;
import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRow;

import java.util.function.Supplier;

/**
 * Simple configurable implementation of {@link EventCreator} that encapsulates the error and its key is the same class.
 *
 * @param <R> the input row
 * @param <E> the simple error
 */
public final class SimpleKeyCreator<R extends InputRow, E extends InputError.Key<R> & InputError<R, E>> extends EventCreator<R, E, E> {

    private final Supplier<E> errorCreator;

    public SimpleKeyCreator(Supplier<E> errorCreator) {
        this.errorCreator = errorCreator;
    }

    @Override
    protected E newInstance() {
        return errorCreator.get();
    }

}
