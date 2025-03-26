package io.github.up2jakarta.xml.api;

public interface IValidationError {

    SeverityType getSeverity();

    Throwable getLinkedException();

    String getMessage();

    int getColumnNumber();

    int getLineNumber();

}
