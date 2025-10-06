package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.hdl.*;
import io.github.up2jakarta.csv.core.EventCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.LazyCounter;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Input Event creator that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful for error persistence.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public class FullCollector<R extends IRecordEntity<?>, D extends DataType<D>, E extends IErrorEntity<R, ?, D>> extends EventCollector<R, D, E> {

    private final Set<E> errors = new LinkedHashSet<>();
    private final IErrorCreator<R, D, E> creator;
    private final LazyCounter counter;

    /**
     * Public constructor fo instance creation.
     *
     * @param row        the input segment
     * @param creator    the error creator
     * @param repository the input repository
     */
    public FullCollector(R row, IEntityCreator<R, D, E> creator, IErrorRepository<R> repository) {
        super(row);
        this.creator = creator;
        this.counter = new LazyCounter(() -> repository.max(row));
    }

    @Override
    protected void accept(D data, int offset, SeverityType type, String code, PropertyException cause) {
        final int order = counter.getAsInt() + errors.size();
        final E error = creator.create(row, offset, data, cause);
        if (error.getKey() != null) {
            error.getKey().setOrder(order);
        }
        errors.add(error);
    }

    @Override
    public final Set<E> toCollection() {
        return errors;
    }

}
