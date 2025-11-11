package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Identifiable;

/**
 * Extension of {@link BusinessCollector}, useful when each row have its related errors.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
public class SelfBusinessCollector<D extends DataType<D>, R extends ISelfRecord<D, ?, E, R> & Identifiable<?>, E extends ISelfEvent<D, R, E> & IBusinessEvent<D, R, ?>> extends BusinessCollector<D, R, E> {

    /**
     * Public constructor fo instance creation.
     *
     * @param source     the input record
     * @param creator    the error creator
     * @param repository the input repository
     */
    public SelfBusinessCollector(R source, IBusinessCreator<D, R, E> creator, IBusinessRepository<R> repository) {
        super(source, source.getErrors(), creator, repository);
    }

}
