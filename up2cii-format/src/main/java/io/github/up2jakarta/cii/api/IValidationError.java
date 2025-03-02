package io.github.up2jakarta.cii.api;

import io.github.up2jakarta.csv.extension.SeverityType;

public interface IValidationError {

    SeverityType getSeverity();

    Throwable getLinkedException();

    String getMessage();

    int getColumnNumber();

    int getLineNumber();

}
