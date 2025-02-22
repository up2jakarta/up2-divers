package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.core.EventCreator;
import io.github.up2jakarta.csv.extension.DataType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;

import java.util.function.Supplier;

/**
 * Simple configurable implementation of {@link EventCreator} that encapsulates the error and its key is the same class.
 *
 * @param <R> the input row
 * @param <E> the simple error
 */
public class SimpleKeyCreator<R extends InputSegment, D extends DataType<D>, E extends InputError.Key<R> & InputError<R, E, D>> extends EventCreator<R, E, D, E> {

    private final Supplier<E> errorCreator;

    /**
     * Constructor with error creator since key is encapsulated by error.
     *
     * @param errorCreator the error creator
     */
    public SimpleKeyCreator(Supplier<E> errorCreator) {
        this.errorCreator = errorCreator;
    }

    @Override
    protected final E newInstance() {
        return errorCreator.get();
    }

}
