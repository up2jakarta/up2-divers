package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.clv.PropertyException;

/**
 * Input Event creator that is responsible for create the final Event to be collected during the mapping/parsing.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
@FunctionalInterface
public interface IErrorCreator<R extends IRecord<?>, D extends DataType<D>, E extends IError<D>> {

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
