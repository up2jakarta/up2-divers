package io.github.up2jakarta.xml.api;


import org.xml.sax.SAXParseException;

public class SAXParseError implements IValidationError {

    private final SeverityType severity;
    private final SAXParseException exception;
    private final String message;

    public SAXParseError(SeverityType level, SAXParseException exception, MessageEnhancer enhancer) {
        this.severity = level;
        this.exception = exception;
        this.message = enhancer.enhance(exception, exception.getMessage());
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
