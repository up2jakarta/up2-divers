package io.github.up2jakarta.csv.api;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Contract interface for creator of events related to an input record, useful for events collector.
 *
 * @param <R> the input record type
 * @param <E> the event type
 */
public interface ICreator<R extends IRecord<?>, E extends IEvent<?>> extends Function<R, List<E>> {

    /**
     * Creates and returns the list of events related to the specified record.
     *
     * @param record the input record source
     */
    @Override
    default List<E> apply(R record) {
        return new LinkedList<>();
    }

}
