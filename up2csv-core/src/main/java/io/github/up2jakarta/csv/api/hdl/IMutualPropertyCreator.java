package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.IMutual;

import java.util.List;

/**
 * {@link IPropertyEvent} for mutual coupled records and events.
 *
 * @param <R> the mutual record type
 * @param <D> the business data type
 * @param <E> the mutual event type
 * @see IBusinessEvent
 * @see IMutualRecord
 */
@FunctionalInterface
public interface IMutualPropertyCreator<D extends DataType<D>, R extends IRecord<?> & IMutualRecord<E, R>, E extends IPropertyEvent<D, R> & IMutual<R, E>> extends IPropertyCreator<D, R, E> {

    @Override
    default List<E> apply(R record) {
        return record.getEvents();
    }

}
