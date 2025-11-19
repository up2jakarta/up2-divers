package io.github.up2jakarta.xml;

import io.github.up2jakarta.xml.api.AbstractCollector;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.MessageEnhancer;
import io.github.up2jakarta.xml.api.ValidationError;
import jakarta.xml.bind.ValidationEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FailSafeHandler extends AbstractCollector {

    private final List<IValidationError> errors = new LinkedList<>();
    private final MessageEnhancer enhancer;

    public FailSafeHandler(MessageEnhancer enhancer, boolean lenient) {
        super(lenient);
        this.enhancer = enhancer;
    }

    @Override
    public boolean handleEvent(ValidationEvent event) {
        final int level = computeLevel(event);
        var ln = event.getLocator().getLineNumber();
        var cn = event.getLocator().getColumnNumber();
        var add = true;
        for (var it = errors.listIterator(); it.hasNext(); ) {
            var e = it.next();
            if (e.getLineNumber() == ln && e.getLineOffset() == cn) {
                if (e.getLevel().getAsInt() < level) {
                    it.remove();
                } else {
                    add = false;
                }
                break;
            }
        }
        if (add) {
            errors.add(new ValidationError(event, level, enhancer));
        }
        return true; // continue validation
    }

    @Override
    public List<IValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}