package io.github.up2jakarta.xml.api;

import io.github.up2jakarta.lov.SeverityType;

public interface IValidationError {

    /**
     * @return the error level
     */
    SeverityType getLevel();

    /**
     * @return the linked exception
     */
    Throwable getLinkedException();

    /**
     * @return the error message
     */
    String getMessage();

    /**
     * @return the error offset in the related line.
     */
    int getLineOffset();

    /**
     * @return the line number
     */
    int getLineNumber();

}
