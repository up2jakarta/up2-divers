package io.github.up2jakarta.lov;

/**
 * Contact interface for an input error.
 */
public interface IError {

    /**
     * @return the error level
     */
    SeverityType getLevel();

    /**
     * @return the error message
     */
    String getMessage();

    /**
     * @return the error code
     */
    String getCode();

}
