package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.lov.core.Identifiable;

/**
 * Contract interface for input repository that is able to count the events related to the given input record
 * before processing data, helpful for well computing the event order.
 *
 * @param <R> the input record type
 * @see io.github.up2jakarta.csv.api.hdl.IBusinessEvent.IKey#getOrder()
 * @see io.github.up2jakarta.csv.core.hdl.BusinessCollector
 * @see io.github.up2jakarta.csv.data.LazyCounter
 */
@FunctionalInterface
public interface IBusinessRepository<R extends IRecord<?> & Identifiable<?>> {

    /**
     * Get and return the max key-order of existing events related to the given input record.
     *
     * @param record the input record
     * @return the max of used key-order
     */
    int max(R record);

}
