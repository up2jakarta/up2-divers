package io.github.up2jakarta.xml.api;

import io.github.up2jakarta.lov.SeverityType;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;

import java.util.List;

public abstract class AbstractCollector implements ValidationEventHandler {

    protected final boolean lenient;

    protected AbstractCollector(boolean strict) {
        this.lenient = strict;
    }

    protected int computeLevel(ValidationEvent event) {
        var level = event.getSeverity();
        if (lenient && event.getMessage().startsWith("unexpected element")) {
            level = ValidationEvent.WARNING;
        }
        return level;
    }

    public void throwValidationExceptionWhenError() throws XValidationException {
        List<IValidationError> errors = getErrors();
        if (lenient) {
            errors = errors.stream().filter(e -> e.getLevel() != SeverityType.WARNING).toList();
        }
        if (!errors.isEmpty()) {
            final XValidationException cause = new XValidationException(errors.getFirst());
            if (errors.size() == 1) {
                throw cause;
            }
            final List<XValidationException> causes = errors.stream().map(XValidationException::new).toList();
            throw new XMultipleException(cause, List.copyOf(causes));
        }
    }

    public abstract List<IValidationError> getErrors();
}