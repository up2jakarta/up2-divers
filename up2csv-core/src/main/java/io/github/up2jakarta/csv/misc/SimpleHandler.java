package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.core.DefaultHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputRepository;
import io.github.up2jakarta.csv.input.InputSegment;

/**
 * Simple implementation handler that uses {@link SimpleKeyCreator}.
 */
public class SimpleHandler<R extends InputSegment<?>, D extends DataType<D>, E extends InputError.Key<R> & InputError<R, E, D>> extends DefaultHandler<R, E, D, E> {

    /**
     * Public constructor fo instance creation.
     *
     * @param row        the input segment
     * @param creator    the error creator
     * @param repository the input repository
     */
    public SimpleHandler(R row, SimpleKeyCreator<R, D, E> creator, InputRepository<R> repository) {
        super(row, creator, repository);
    }

}
