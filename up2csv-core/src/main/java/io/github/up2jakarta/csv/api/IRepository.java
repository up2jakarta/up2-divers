package io.github.up2jakarta.csv.api;

/**
 * Contract interface for input repository that is able to count the errors related to the given input record
 * before processing data, helpful for well computing the error order.
 *
 * @param <R> the input record type
 * @see io.github.up2jakarta.csv.api.hdl.ITraceEvent.IKey#getOrder()
 * @see io.github.up2jakarta.csv.core.hdl.TraceCollector
 * @see io.github.up2jakarta.csv.data.LazyCounter
 */
@FunctionalInterface
public interface IRepository<R extends IRecord<?>> {

    /**
     * Get and return the max key-order of existing errors related to the given input row.
     *
     * @param row the input row
     * @return the max of used key-order
     */
    int max(R row);

}
