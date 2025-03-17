package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.extension.DataType;
import io.github.up2jakarta.csv.extension.SeverityType;

/**
 * Contact interface for an input error.
 *
 * @param <R> the input row type
 * @param <K> the identifier type of input error
 */
public interface InputError<R extends InputSegment<?>, K extends InputError.Key<R>, D extends DataType<D>> {

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
     * @param type the source of data
     */
    void setType(D type);

    /**
     * @param message the error message
     */
    void setMessage(String message);

    /**
     * @param trace the error stack trace
     */
    void setTrace(String trace);

    /**
     * Contact interface for input error identifier.
     *
     * @param <R> the input row type
     */
    interface Key<R extends InputSegment<?>> {

        /**
         * @param order the error order in the list of errors related th the row
         */
        void setOrder(Integer order);

        /**
         * @param record the related input record
         */
        void setRecord(R record);

    }

}
