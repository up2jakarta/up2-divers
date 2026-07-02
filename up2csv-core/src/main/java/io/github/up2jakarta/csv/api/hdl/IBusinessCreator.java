package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.ICreator;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.IError;

/**
 * Input Event creator that's able to create {@link IBusinessEvent} to be collected during the mapping/parsing,
 * useful for events persistence.
 *
 * @param <R> the input record type
 * @param <D> the business term type
 * @param <E> the event type
 * @see IBusinessEvent
 * @see io.github.up2jakarta.csv.core.hdl.BusinessCollector#MODE
 */
@FunctionalInterface
public interface IBusinessCreator<D extends ITerm<D>, R extends IRecord<?>, E extends IBusinessEvent<D, R, ?>> extends ICreator<R, E> {

    /**
     * Creates and returns the input event that is being full-filled from the specified arguments.
     *
     * @param record the input record source
     * @param order  the event order
     * @param type   the business term
     * @param offset the input data index
     * @param cause  the event cause
     * @param trace  the stack trace of the cause exception
     * @return the full-filled input event
     */
    E apply(R record, int order, D type, Integer offset, IError cause, String trace);

}
