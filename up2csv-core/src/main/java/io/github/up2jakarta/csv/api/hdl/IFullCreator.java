package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.IError;


/**
 * Input Event {@link IFullError} that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful for error persistence.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
@FunctionalInterface
public interface IFullCreator<R extends IFullRecord<?, ?, ?>, D extends DataType<D>, E extends IFullError<R, ?, D>> {

    /**
     * Create and return the input error that is being full-filled from the given arguments.
     *
     * @param row   the input row source
     * @param order the error order
     * @param type  the input data type
     * @param index the input data index
     * @param cause the error cause
     * @param trace the stack trace of the cause exception
     * @return the full-filled input error
     */
    E create(R row, int order, D type, int index, IError cause, String trace);

}
