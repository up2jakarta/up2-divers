package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IMutual;

import java.util.List;

/**
 * Contact interface for an input record, useful when each record have its related events.
 *
 * @param <R> the mutual record type
 * @param <E> the mutual event type
 * @see IMutualBusinessCreator
 * @see IMutualPropertyCreator
 */
public interface IMutualRecord<E extends IMutual<R, E>, R extends IMutualRecord<E, R>> extends IMutual<E, R> {

    /**
     * @return the list of related events
     */
    List<E> getEvents();

}
