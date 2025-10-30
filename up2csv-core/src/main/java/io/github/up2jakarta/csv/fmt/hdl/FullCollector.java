package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.hdl.IFullCreator;
import io.github.up2jakarta.csv.api.hdl.IFullError;
import io.github.up2jakarta.csv.api.hdl.IFullRecord;
import io.github.up2jakarta.csv.api.hdl.IFullRepository;
import io.github.up2jakarta.csv.core.hdl.Event;
import io.github.up2jakarta.csv.core.hdl.EventCollector;
import io.github.up2jakarta.csv.core.hdl.EventType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.LazyCounter;

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
public class FullCollector<R extends IFullRecord<?, ?, ?>, D extends DataType<D>, E extends IFullError<R, ?, D>> extends EventCollector<R, D, E, Event> {

    private final Set<E> errors = new LinkedHashSet<>();
    private final IFullCreator<R, D, E> creator;
    private final LazyCounter counter;

    /**
     * Public constructor fo instance creation.
     *
     * @param row        the input segment
     * @param creator    the error creator
     * @param repository the input repository
     */
    public FullCollector(R row, IFullCreator<R, D, E> creator, IFullRepository<R> repository) {
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
