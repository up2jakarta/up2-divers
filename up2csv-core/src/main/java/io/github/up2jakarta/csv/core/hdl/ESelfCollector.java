package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.ICauseCreator;
import io.github.up2jakarta.csv.api.hdl.ISelfEvent;
import io.github.up2jakarta.csv.api.hdl.ISelfRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

import java.util.List;

/**
 * Input events collector that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful when each row have its related errors based on cause exceptions only.
 * <p>
 * This collector is the default mode for {@link io.github.up2jakarta.csv.BusinessBuilder} when working with
 * {@link ICauseCreator} for events based on cause exceptions, it's compatible for all modes.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
public class ESelfCollector<D extends DataType<D>, R extends ISelfRecord<D, ?, E, R>, E extends ISelfEvent<D, R, E>> extends EventCollector<R, D, E, PropertyException> {

    private final ICauseCreator<R, D, E> creator;

    /**
     * Public constructor fo instance creation.
     *
     * @param row     the input segment
     * @param creator the error creator
     */
    public ESelfCollector(R row, ICauseCreator<R, D, E> creator) {
        super(row, CAUSE_TYPE);
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
