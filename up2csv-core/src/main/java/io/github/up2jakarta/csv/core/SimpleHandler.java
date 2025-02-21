package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputRepository;
import io.github.up2jakarta.csv.input.InputRow;
import io.github.up2jakarta.csv.misc.SimpleKeyCreator;

/**
 * Simple implementation handler that uses {@link SimpleKeyCreator}.
 */
public class SimpleHandler<R extends InputRow, E extends InputError.Key<R> & InputError<R, E>> extends DefaultHandler<R, E, E> {

    /**
     * Public constructor fo instance creation.
     *
     * @param row        the input segment
     * @param creator    the error creator
     * @param repository the input repository
     */
    public SimpleHandler(R row, SimpleKeyCreator<R, E> creator, InputRepository<R> repository) {
        super(row, creator, repository);
    }

}
