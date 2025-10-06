package io.github.up2jakarta.xml.api;

/**
 * Contact interface for an input error.
 */
public interface IError {

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
