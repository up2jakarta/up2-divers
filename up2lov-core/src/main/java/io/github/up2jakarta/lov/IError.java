package io.github.up2jakarta.lov;

import java.io.Serializable;

/**
 * Contact interface for an input error.
 */
public interface IError extends Serializable {

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
