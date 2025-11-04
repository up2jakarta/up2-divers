package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.ICauseCreator;
import io.github.up2jakarta.csv.api.hdl.IRecordCollector;
import io.github.up2jakarta.csv.api.hdl.IRecordEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

import java.util.List;

/**
 * Input events collector that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful when each row have its related errors based on exceptions only.
 * <p>
 * This collector is the default mode for {@link io.github.up2jakarta.csv.BusinessBuilder} when working with
 * {@link ICauseCreator} for events based on exceptions, it's compatible for all modes.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
public class RecordCollector<D extends DataType<D>, E extends IRecordEvent<D, R, E>, R extends IRecordCollector<D, ?, E, R>> extends EventCollector<R, D, E, PropertyException> {

    private final ICauseCreator<R, D, E> creator;

    /**
     * Public constructor fo instance creation.
     *
     * @param row     the input segment
     * @param creator the error creator
     */
    public RecordCollector(R row, ICauseCreator<R, D, E> creator) {
        super(row, EXCEPTION_TYPE);
        this.creator = creator;
    }

    @Override
    protected void accept(D data, int offset, PropertyException cause) {
        final E error = creator.create(row, offset, data, cause);
        row.getErrors().add(error);
    }

    @Override
    public final List<E> toCollection() {
        return row.getErrors();
    }

}
