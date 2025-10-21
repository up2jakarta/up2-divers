package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.hdl.IEntityCreator;
import io.github.up2jakarta.csv.api.hdl.IErrorEntity;
import io.github.up2jakarta.csv.api.hdl.IErrorRepository;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.core.Up2Collector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.LazyCounter;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.util.LinkedHashSet;
import java.util.Set;

import static io.github.up2jakarta.csv.api.hdl.IEntityCreator.trace;

/**
 * Input Event creator that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful for error persistence.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public class FullCollector<R extends IRecordEntity<?, ?, ?>, D extends DataType<D>, E extends IErrorEntity<R, ?, D>> extends Up2Collector<R, D, E> {

    private final Set<E> errors = new LinkedHashSet<>();
    private final IEntityCreator<R, D, E> creator;
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
    protected void accept(D dt, int di, SeverityType et, String ec, PropertyException ex) {
        final int order = counter.getAsInt() + errors.size();
        final E error = creator.create(row, order, dt, di, ex.getSeverity(), ex.getCode(), ex.getMessage(), trace(ex));
        errors.add(error);
    }

    @Override
    public final Set<E> toCollection() {
        return errors;
    }

}
