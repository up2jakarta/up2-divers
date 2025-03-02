package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.MessageEnhancer;
import io.github.up2jakarta.csv.extension.SeverityType;
import org.xml.sax.SAXParseException;

public class SAXParseError implements IValidationError {

    private final SeverityType severity;
    private final SAXParseException exception;
    private final String message;

    public SAXParseError(SeverityType level, SAXParseException exception) {
        this.severity = level;
        this.exception = exception;
        this.message = MessageEnhancer.enhance(exception, exception.getMessage());
    }

    @Override
    public SeverityType getSeverity() {
        return severity;
    }

    @Override
    public SAXParseException getLinkedException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getColumnNumber() {
        return exception.getColumnNumber();
    }

    @Override
    public int getLineNumber() {
        return exception.getLineNumber();
    }
}
