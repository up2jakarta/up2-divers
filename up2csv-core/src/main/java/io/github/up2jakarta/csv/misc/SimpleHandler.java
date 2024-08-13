package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.persistence.InputRow;

/**
 * Simple implementation handler that uses {@link SimpleKeyCreator}that encapsulates the error and its key is the same class.
 *
 * @param <R> the input row type
 * @param <E> the error type
 */
public final class SimpleHandler<R extends InputRow, E extends InputError.Key<R> & InputError<R, E>> extends EventHandler<R, E, E> {

    public SimpleHandler(R row, SimpleKeyCreator<R, E> creator, InputRepository<R> repository) {
        super(row, creator, repository);
    }

}
