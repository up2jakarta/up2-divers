package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent.IKey;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.core.Identifiable;

/**
 * Contact interface for an input event with trace property instead of exception, useful for events persistence.
 *
 * @param <R> the input record type
 * @param <K> the event key type
 * @param <D> the business term type
 * @see IBusinessCreator
 * @see io.github.up2jakarta.csv.core.hdl.BusinessCollector#MODE
 */
public interface IBusinessEvent<D extends ITerm<D>, R extends IRecord<?>, K extends IKey<R>> extends IEvent<D>, Identifiable<K> {

    /**
     * @return the event stack trace
     */
    String getTrace();

    /**
     * Contact interface for input event identifier.
     *
     * @param <R> the input record type
     */
    interface IKey<R extends IRecord<?>> {

        /**
         * @return the computed order by record
         */
        int getOrder();

        /**
         * @return the related input record
         */
        R getRecord();

    }

}
