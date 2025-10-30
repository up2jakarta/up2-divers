package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.hdl.IFullError.IKey;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Identifiable;

/**
 * Contact interface for an input error with cause property, useful for error persistence.
 *
 * @param <R> the record type
 * @param <K> the error key type
 * @param <D> the input record type
 */
public interface IFullError<D extends DataType<D>, R extends IFullRecord<?, ?, ?>, K extends IKey<R>> extends IEvent<D>, Identifiable<K> {

    /**
     * @return the the error stack trace
     */
    String getTrace();

    /**
     * Contact interface for input error identifier.
     *
     * @param <R> the input row type
     */
    interface IKey<R extends IFullRecord<?, ?, ?>> {

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
