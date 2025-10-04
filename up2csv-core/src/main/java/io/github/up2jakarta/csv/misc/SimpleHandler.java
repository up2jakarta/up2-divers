package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IErrorRepository;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.DefaultHandler;
import io.github.up2jakarta.csv.data.DataType;

/**
 * Simple implementation handler that uses {@link SimpleKeyCreator}.
 */
public class SimpleHandler<R extends IRecord<?>, D extends DataType<D>, E extends IError.Key<R> & IError<R, E, D>> extends DefaultHandler<R, E, D, E> {

    /**
     * Public constructor fo instance creation.
     *
     * @param row        the input segment
     * @param creator    the error creator
     * @param repository the input repository
     */
    public SimpleHandler(R row, SimpleKeyCreator<R, D, E> creator, IErrorRepository<R> repository) {
        super(row, creator, repository);
    }

}
