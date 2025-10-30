package io.github.up2jakarta.xml.api;

import java.io.Serializable;

/**
 * Contact interface for an input error.
 */
public interface IError extends Serializable {

    /**
     * @return the error severity level
     */
    SeverityType getSeverity();

    /**
     * @return the error message
     */
    String getMessage();

    /**
     * @return the error code
     */
    String getCode();

}
