package io.github.up2jakarta.xml.api;


import io.github.up2jakarta.lov.SeverityType;
import org.xml.sax.SAXParseException;

public class SAXParseError implements IValidationError {

    private final SAXParseException exception;
    private final SeverityType level;
    private final String message;

    public SAXParseError(SeverityType level, SAXParseException exception, MessageEnhancer enhancer) {
        this.level = level;
        this.exception = exception;
        this.message = enhancer.enhance(exception, exception.getMessage());
    }

    @Override
    public SeverityType getLevel() {
        return level;
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
    public int getLineOffset() {
        return exception.getColumnNumber();
    }

    @Override
    public int getLineNumber() {
        return exception.getLineNumber();
    }
}
