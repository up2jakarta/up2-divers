package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.core.ErrorEnhancer;
import io.github.up2jakarta.xml.FailSafeHandler;
import io.github.up2jakarta.xml.XBuilder;
import io.github.up2jakarta.xml.api.*;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.cii.CII.*;
import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static java.util.Collections.singletonList;

/**
 * Thread-safe processor that checks CII-D16B invoice is readable (Syntax and Data-format).
 */
public class InvoiceValidator<I> extends XBuilder<I> implements XValidator<I> {

    public InvoiceValidator(Class<I> type, final XmlAdapter<?, ?>... adapters) {
        super(CII_QNAME, type, getBuilder(), getSchema(), adapters);
    }

    private static List<IValidationError> getOrThrowError(JAXBException ex) {
        if (ex.getLinkedException() instanceof SAXParseException sax) {
            return singletonList(new SAXParseError(FATAL, sax, ErrorEnhancer::enhance));
        }
        throw new XValidationException(ex);
    }

    @Override
    public List<IValidationError> validate(File xmlFile) throws IOException {
        return this.validate(newStreamSource(xmlFile));
    }

    @Override
    public List<IValidationError> validate(StreamSource xmlFile) throws IOException {
        var handler = new FailSafeHandler(ErrorEnhancer::enhance, false);
        try {
            var unmarshaller = newUnmarshaller(handler);
            unmarshaller.unmarshal(xmlFile, type);
        } catch (JAXBException ex) {
            return getOrThrowError(ex);
        }
        return handler.getErrors();
    }

    @Override
    public List<IValidationError> validate(Document xmlDocument) {
        var handler = new FailSafeHandler(ErrorEnhancer::enhance, false);
        try {
            var unmarshaller = newUnmarshaller(handler);
            unmarshaller.unmarshal(xmlDocument, type);
        } catch (JAXBException ex) {
            return getOrThrowError(ex);
        }
        return handler.getErrors();
    }

    @Override
    public List<IValidationError> validate(I invoice) {
        var handler = new FailSafeHandler(ErrorEnhancer::enhance, false);
        try {
            var document = newDocument();
            var marshaller = newMarshaller(handler, CII::config);
            var root = newElement(invoice);
            marshaller.marshal(root, document);
        } catch (JAXBException ex) {
            return getOrThrowError(ex);
        } catch (ParserConfigurationException e) {
            throw new XConfigurationException("Cannot create XML document", e);
        }
        return handler.getErrors();
    }

}
