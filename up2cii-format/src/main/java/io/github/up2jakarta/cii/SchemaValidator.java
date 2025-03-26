package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.core.ErrorEnhancer;
import io.github.up2jakarta.cii.format.standard.CrossIndustryInvoiceType;
import io.github.up2jakarta.xml.SchemaCollector;
import io.github.up2jakarta.xml.XProcessor;
import io.github.up2jakarta.xml.api.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static io.github.up2jakarta.cii.CII.CII_SCHEMA;

/**
 * Thread-safe processor that checks CII-D16B invoices against Schema (Syntax).
 *
 * @see CrossIndustryInvoiceType
 */
public class SchemaValidator extends XProcessor<CrossIndustryInvoiceType> implements IValidator<IValidationError> {

    public SchemaValidator() {
        super(CrossIndustryInvoiceType.class, CII_SCHEMA, null);
    }

    private Validator createValidator() {
        try {
            var validator = schema.newValidator();
            validator.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, CII.CII_ALLOWED_PROTOCOL);
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, CII.CII_ALLOWED_PROTOCOL);
            return validator;
        } catch (SAXNotSupportedException | SAXNotRecognizedException e) {
            throw new XConfigurationException("Cannot create XML validator", e);
        }
    }

    @Override
    public List<IValidationError> validate(File xmlFile) throws IOException {
        return this.validate(newStreamSource(xmlFile));
    }

    @Override
    public List<IValidationError> validate(StreamSource xmlFile) throws IOException {
        var handler = new SchemaCollector(ErrorEnhancer::enhance);
        var validator = createValidator();
        validator.setErrorHandler(handler);
        try {
            validator.validate(xmlFile);
        } catch (SAXParseException e) {
            return Collections.singletonList(new SAXParseError(SeverityType.FATAL, e, ErrorEnhancer::enhance));
        } catch (SAXException e) {
            throw new XValidationException(e);
        }
        return handler.getErrors();
    }

}
