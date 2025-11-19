package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.ICreator;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.PropertyException;

/**
 * Input Event creator that's able to create {@link IPropertyEvent} to be collected during the mapping/parsing,
 * useful for events logging
 *
 * @param <R> the input record type
 * @param <D> the business data type
 * @param <E> the  event type
 * @see IPropertyEvent
 * @see io.github.up2jakarta.csv.core.hdl.PropertyCollector#MODE
 */
@FunctionalInterface
public interface IPropertyCreator<D extends DataType<D>, R extends IRecord<?>, E extends IPropertyEvent<D, R>> extends ICreator<R, E> {

    /**
     * Creates and returns the input event that is being full-filled from the specified arguments.
     *
     * @param record the input record source
     * @param offset the input index in the related record
     * @param cause  the event cause
     * @param type   the business data type
     * @return the full-filled input event
     */
    E apply(R record, Integer offset, D type, PropertyException cause);

}
