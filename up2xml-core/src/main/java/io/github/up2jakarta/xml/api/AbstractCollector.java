package io.github.up2jakarta.xml.api;

import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;

import java.util.List;

public abstract class AbstractCollector implements ValidationEventHandler {

    protected final boolean lenient;

    public AbstractCollector(boolean strict) {
        this.lenient = strict;
    }

    protected int computeSeverity(ValidationEvent event) {
        var level = event.getSeverity();
        if (lenient && event.getMessage().startsWith("unexpected element")) {
            level = ValidationEvent.WARNING;
        }
        return level;
    }

    public void throwValidationExceptionWhenError() throws XValidationException {
        List<IValidationError> errors = getErrors();
        if (lenient) {
            errors = errors.stream().filter(e -> e.getSeverity() != SeverityType.WARNING).toList();
        }
        if (!errors.isEmpty()) {
            final XValidationException cause = new XValidationException(errors.get(0));
            if (errors.size() == 1) {
                throw cause;
            }
            final List<XValidationException> causes = errors.stream().map(XValidationException::new).toList();
            throw new XMultipleException(cause, causes);
        }
    }

    public abstract List<IValidationError> getErrors();
}