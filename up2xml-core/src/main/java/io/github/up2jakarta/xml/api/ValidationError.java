package io.github.up2jakarta.xml.api;

import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.xml.bind.ValidationEvent;

public class ValidationError implements IValidationError {

    private final SeverityType level;
    private final ValidationEvent event;
    private final String message;
    private final Throwable linkedException;

    public ValidationError(ValidationEvent event, int level, MessageEnhancer enhancer) {
        this.event = event;
        this.level = SeverityType.of(level);
        final Throwable cause = MessageEnhancer.getCause(event.getLinkedException(), CodeListException.class);
        if (cause == null) {
            this.linkedException = event.getLinkedException();
            this.message = enhancer.enhance(this.linkedException, event.getMessage());
        } else {
            this.linkedException = cause;
            this.message = enhancer.enhance(this.linkedException, cause.getMessage());
        }
    }

    @Override
    public SeverityType getLevel() {
        return level;
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
    public int getLineOffset() {
        return event.getLocator().getColumnNumber();
    }

    @Override
    public int getLineNumber() {
        return event.getLocator().getLineNumber();
    }

}
