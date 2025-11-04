package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IRepository;
import io.github.up2jakarta.csv.api.hdl.ITraceCreator;
import io.github.up2jakarta.csv.api.hdl.ITraceEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.LazyCounter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Input events collector that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful for error persistence.
 * <p>
 * This collector is the default mode for {@link io.github.up2jakarta.csv.BusinessBuilder} when working with
 * {@link ITraceCreator} and {@link IRepository}, it's compatible for all modes.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public class TraceCollector<D extends DataType<D>, R extends IRecord<?>, E extends ITraceEvent<D, R, ?>> extends EventCollector<R, D, E, Event> {

    private final Set<E> errors = new LinkedHashSet<>();
    private final ITraceCreator<D, R, E> creator;
    private final LazyCounter counter;

    /**
     * Public constructor fo instance creation.
     *
     * @param row        the input segment
     * @param creator    the error creator
     * @param repository the input repository
     */
    public TraceCollector(R row, ITraceCreator<D, R, E> creator, IRepository<R> repository) {
        super(row, ERROR_TYPE);
        this.creator = creator;
        this.counter = new LazyCounter(() -> repository.max(row));
    }

    @Override
    protected void accept(D type, int offset, Event cause) {
        final int order = counter.getAsInt() + errors.size();
        final String trace = EventType.trace(cause).orElse(null);
        final E error = creator.create(row, order, type, offset, cause, trace);
        errors.add(error);
    }

    @Override
    public final Set<E> toCollection() {
        return errors;
    }

}
