package io.github.up2jakarta.xml;

import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.xml.api.AbstractCollector;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.XValidationException;
import jakarta.xml.bind.ValidationEvent;

import java.util.Collections;
import java.util.List;

import static io.github.up2jakarta.xml.api.MessageEnhancer.getCause;

public class FailFastHandler extends AbstractCollector {

    public static final FailFastHandler STRICT_INSTANCE = new FailFastHandler(false);
    public static final FailFastHandler LENIENT_INSTANCE = new FailFastHandler(true);

    private FailFastHandler(boolean lenient) {
        super(lenient);
    }

    @Override
    public boolean handleEvent(ValidationEvent event) {
        final int level = computeLevel(event);
        if (lenient && level == ValidationEvent.WARNING) {
            return true;
        }
        final CodeListException cause = getCause(event.getLinkedException(), CodeListException.class);
        if (cause != null) {
            throw new XValidationException(cause);
        }
        throw new XValidationException(event.getLinkedException());
    }

    @Override
    public List<IValidationError> getErrors() {
        return Collections.emptyList();
    }
}