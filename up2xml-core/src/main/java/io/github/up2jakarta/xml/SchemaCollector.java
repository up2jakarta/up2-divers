package io.github.up2jakarta.xml;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.MessageEnhancer;
import io.github.up2jakarta.xml.api.SAXParseError;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class SchemaCollector implements ErrorHandler {

    private final MessageEnhancer enhancer;
    private final List<IValidationError> errors = new LinkedList<>();

    public SchemaCollector(MessageEnhancer enhancer) {
        this.enhancer = enhancer;
    }

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

    protected void handle(SeverityType level, SAXParseException e) {
        errors.add(new SAXParseError(level, e, enhancer));
    }

    public List<IValidationError> getErrors() {
        return unmodifiableList(errors);
    }
}