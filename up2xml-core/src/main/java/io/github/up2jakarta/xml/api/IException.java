package io.github.up2jakarta.xml.api;

/**
 * Contract interface for a simplified error.
 */
public interface IException extends IError {

    /**
     * @return the error cause.
     */
    Throwable getCause();

}
