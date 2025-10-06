package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.hdl.IErrorEntity.IKey;
import io.github.up2jakarta.csv.data.DataType;

/**
 * Contact interface for an input error with cause property, useful for error persistence.
 *
 * @param <R> the record type
 * @param <K> the error key type
 * @param <D> the input row type
 */
public interface IErrorEntity<R extends IRecordEntity<?>, K extends IKey<R>, D extends DataType<D>> extends IError<D> {

    /**
     * @return the identifier
     */
    K getKey();

    /**
     * @return the the error stack trace
     */
    String getTrace();

    /**
     * Contact interface for input error identifier.
     *
     * @param <R> the input row type
     */
    interface IKey<R extends IRecordEntity<?>> {

        /**
         * @return the computed order by record
         */
        Integer getOrder();

        /**
         * @param order the error order in the list of errors related th the row
         */
        void setOrder(Integer order);

        /**
         * @return the related input record
         */
        R getRecord();

    }

}
