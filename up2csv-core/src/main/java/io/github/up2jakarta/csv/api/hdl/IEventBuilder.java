package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.core.Listable;

/**
 * Contract interface for {@link BusinessHandler} builder.
 * <p>
 * It's responsible to creates {@link BusinessHandler} for multiple records and collects all errors related to each
 * input record during the aggregation of many records to one business-object.
 *
 * @param <D> the business data type
 * @param <R> the input record type
 * @param <E> the event type
 * @see io.github.up2jakarta.csv.core.BusinessImporter
 */
public interface IEventBuilder<D extends DataType<D>, R extends IRecord<?>, E extends IEvent<D>> extends Listable<E> {

    /**
     * Creates and returns a valid event-handler for the specified record.
     *
     * <ul>
     *     It depends on the event type {@link E}
     *   <li>if the error doesn't depend on the specified record then one handler per business-object is enough</li>
     *   <li>if the error depend on the the specified record then it's necessary to create one handler per record</li>
     * </ul>
     *
     * @param record the input record
     * @return a business event-handler, must not be <code>null</code>
     */
    BusinessHandler<D> of(R record);

}
