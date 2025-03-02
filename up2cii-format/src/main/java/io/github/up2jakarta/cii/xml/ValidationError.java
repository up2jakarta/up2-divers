package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.MessageEnhancer;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.SeverityType;
import jakarta.xml.bind.ValidationEvent;

import static io.github.up2jakarta.cii.api.MessageEnhancer.getCause;

public class ValidationError implements IValidationError {

    private final SeverityType severity;
    private final ValidationEvent event;
    private final String message;
    private final Throwable linkedException;

    public ValidationError(ValidationEvent event, int severity) {
        this.event = event;
        this.severity = SeverityType.of(severity);
        final Throwable cause = getCause(event.getLinkedException(), CodeListException.class);
        if (cause == null) {
            this.linkedException = event.getLinkedException();
            this.message = MessageEnhancer.enhance(this.linkedException, event.getMessage());
        } else {
            this.linkedException = cause;
            this.message = MessageEnhancer.enhance(this.linkedException, cause.getMessage());
        }
    }

    @Override
    public SeverityType getSeverity() {
        return severity;
    }

    @Override
    public Throwable getLinkedException() {
        return linkedException;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getColumnNumber() {
        return event.getLocator().getColumnNumber();
    }

    @Override
    public int getLineNumber() {
        return event.getLocator().getLineNumber();
    }

}
