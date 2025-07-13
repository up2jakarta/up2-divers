package io.github.up2jakarta.xml;

import io.github.up2jakarta.xml.api.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Thread-safe processor that checks CII-D16B invoices against Schema (Syntax).
 */
public class SchemaValidator extends XProcessor<Void> implements IValidator<IValidationError> {

    private final MessageEnhancer enhancer;

    public SchemaValidator(Schema xsd, MessageEnhancer enhancer) {
        super(Void.class, xsd);
        this.enhancer = enhancer;
    }

    @Override
    public List<IValidationError> validate(File xmlFile) throws IOException {
        return this.validate(newStreamSource(xmlFile));
    }

    @Override
    public List<IValidationError> validate(StreamSource xmlFile) throws IOException {
        var handler = new SchemaCollector(enhancer);
        var validator = newValidator();
        validator.setErrorHandler(handler);
        try {
            validator.validate(xmlFile);
        } catch (SAXParseException e) {
            return Collections.singletonList(new SAXParseError(SeverityType.FATAL, e, enhancer));
        } catch (SAXException e) {
            throw new XValidationException(e);
        }
        return handler.getErrors();
    }

}
