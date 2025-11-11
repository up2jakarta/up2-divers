package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

/**
 * Input Event creator that's able to create {@link IPropertyEvent} to be collected during the mapping/parsing,
 * useful for error logging
 *
 * @param <R> the input record type
 * @param <D> the input data type
 * @param <E> the input error type
 * @see IPropertyEvent
 * @see io.github.up2jakarta.csv.core.hdl.BusinessHandler#PROPERTY_MODE
 */
@FunctionalInterface
public interface IPropertyCreator<R extends IRecord<?>, D extends DataType<D>, E extends IPropertyEvent<R, D>> {

    /**
     * Create and return the input error that is being full-filled from the given arguments.
     *
     * @param row    the input row source
     * @param offset the input index
     * @param cause  the error cause
     * @param type   the business type
     * @return the full-filled input error
     */
    E create(R row, Integer offset, D type, PropertyException cause);

}
