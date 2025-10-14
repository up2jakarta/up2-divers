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
     * @return the error code for known exception
     */
    String getCode();

    /**
     * @return the error message
     */
    String getMessage();

}
