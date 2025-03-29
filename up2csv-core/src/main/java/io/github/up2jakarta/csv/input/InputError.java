package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;

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
     * @return the input index
     */
    Integer getOffset();

    /**
     * @param offset the input index
     */
    void setOffset(Integer offset);

    /**
     * @return the error severity
     */
    SeverityType getSeverity();

    /**
     * @param severity the error severity
     */
    void setSeverity(SeverityType severity);

    /**
     * @return the error code for known exception
     */
    String getCode();

    /**
     * @param code the error code for known exception
     */
    void setCode(String code);

    /**
     * @param type the source of data
     */
    void setType(D type);

    /**
     * @return the source of data
     */
    D getType();

    /**
     * @return the error message
     */
    String getMessage();

    /**
     * @param message the error message
     */
    void setMessage(String message);

    /**
     * @return the the error stack trace
     */
    String getTrace();

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
