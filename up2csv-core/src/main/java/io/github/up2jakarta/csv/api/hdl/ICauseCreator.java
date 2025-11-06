package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

/**
 * Input Event creator that's able to create {@link ICauseEvent} to be collected during the mapping/parsing,
 * useful for error logging
 *
 * @param <R> the input record type
 * @param <D> the input data type
 * @param <E> the input error type
 * @see ICauseEvent
 * @see io.github.up2jakarta.csv.core.hdl.EventCollector#CAUSE_TYPE
 */
@FunctionalInterface
public interface ICauseCreator<R extends IRecord<?>, D extends DataType<D>, E extends IEvent<D>> {

    /**
     * Create and return the input error that is being full-filled from the given arguments.
     *
     * @param row    the input row source
     * @param offset the input index
     * @param cause  the error cause
     * @param type   the business type
     * @return the full-filled input error
     */
    E create(R row, int offset, D type, PropertyException cause);

}
