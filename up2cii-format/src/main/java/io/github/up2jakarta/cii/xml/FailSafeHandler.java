package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.api.IValidationError;
import jakarta.xml.bind.ValidationEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FailSafeHandler extends AbstractCollector {

    private final List<IValidationError> errors = new LinkedList<>();

    public FailSafeHandler(boolean lenient) {
        super(lenient);
    }

    @Override
    public boolean handleEvent(ValidationEvent event) {
        final int severity = computeSeverity(event);
        var l = event.getLocator().getLineNumber();
        var c = event.getLocator().getColumnNumber();

        var add = true;
        for (var it = errors.listIterator(); it.hasNext(); ) {
            var e = it.next();
            if (e.getLineNumber() == l && e.getColumnNumber() == c) {
                if (e.getSeverity().getLevel() < severity) {
                    it.remove();
                } else {
                    add = false;
                }
                break;
            }
        }
        if (add) {
            errors.add(new ValidationError(event, severity));
        }
        return true; // continue validation
    }

    @Override
    public List<IValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}