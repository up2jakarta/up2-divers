package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.csv.extension.SeverityType;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class SchemaErrorCollector implements ErrorHandler {

    private final List<IValidationError> errors = new LinkedList<>();

    @Override
    public final void warning(final SAXParseException ex) {
        handle(SeverityType.WARNING, ex);
    }

    @Override
    public final void error(final SAXParseException ex) {
        handle(SeverityType.ERROR, ex);
    }

    @Override
    public final void fatalError(final SAXParseException ex) {
        handle(SeverityType.FATAL, ex);
    }

    protected void handle(SeverityType iErrorLevel, SAXParseException e) {
        errors.add(new SAXParseError(iErrorLevel, e));
    }

    public List<IValidationError> getErrors() {
        return unmodifiableList(errors);
    }
}