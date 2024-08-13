package io.github.up2jakarta.csv.persistence;

import io.github.up2jakarta.csv.misc.SeverityType;

/**
 * Contact interface for an input error.
 *
 * @param <R> the input row type
 * @param <K> the identifier type of input error
 */
public interface InputError<R extends InputRow, K extends InputError.Key<R>> {

    /**
     * @return the identifier
     */
    K getKey();

    /**
     * @param ignore the error key
     */
    default void setKey(K ignore) {
        throw new IllegalStateException("I'm the the key");
    }

    /**
     * @param offset the input index
     */
    void setOffset(Integer offset);

    /**
     * @param severity the error severity
     */
    void setSeverity(SeverityType severity);

    /**
     * @param code the error code for known exception
     */
    void setCode(String code);

    /**
     * @param message the error message
     */
    void setMessage(String message);

    /**
     * Contact interface for input error identifier.
     *
     * @param <R> the input row type
     */
    interface Key<R extends InputRow> {

        /**
         * @param order the error order in the list of errors related th the row
         */
        void setOrder(Integer order);

        /**
         * @param row the related input row
         */
        void setRow(R row);

    }

}
