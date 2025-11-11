package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.IError;


/**
 * Input Event creator that's able to create {@link IBusinessEvent} to be collected during the mapping/parsing,
 * useful for error persistence.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 * @see IBusinessEvent
 * @see io.github.up2jakarta.csv.core.hdl.BusinessHandler#BUSINESS_MODE
 */
@FunctionalInterface
public interface IBusinessCreator<D extends DataType<D>, R extends IRecord<?>, E extends IBusinessEvent<D, R, ?>> {

    /**
     * Create and return the input error that is being full-filled from the given arguments.
     *
     * @param row    the input row source
     * @param order  the error order
     * @param type   the input data type
     * @param offset the input data index
     * @param cause  the event cause
     * @param trace  the stack trace of the cause exception
     * @return the full-filled input error
     */
    E create(R row, int order, D type, Integer offset, IError cause, String trace);

}
