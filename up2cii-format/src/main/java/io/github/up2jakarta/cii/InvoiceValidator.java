package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XConfigurationException;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.cii.api.XValidator;
import io.github.up2jakarta.cii.xml.FailSafeHandler;
import io.github.up2jakarta.cii.xml.SAXParseError;
import io.github.up2jakarta.cii.xml.XProcessor;
import io.github.up2jakarta.csv.extension.SeverityType;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.cii.CII.XSD_URL;
import static java.util.Collections.singletonList;

/**
 * Thread-safe processor that checks CII-D16B invoice is readable (Syntax and Data-format).
 */
public class InvoiceValidator<I> extends XProcessor<I> implements XValidator<I> {

    public InvoiceValidator(Class<I> type, final XmlAdapter<?, ?>[] adapters) {
        super(type, XSD_URL, adapters);
    }

    private static List<IValidationError> getOrThrowError(JAXBException ex) {
        if (ex.getLinkedException() instanceof SAXParseException) {
            return singletonList(new SAXParseError(SeverityType.FATAL, (SAXParseException) ex.getLinkedException()));
        }
        throw new XValidationException(ex);
    }

    @Override
    public List<IValidationError> validate(File xmlFile) throws IOException {
        return this.validate(getStreamSource(xmlFile));
    }

    @Override
    public List<IValidationError> validate(StreamSource xmlFile) throws IOException {
        var handler = new FailSafeHandler(false);
        try {
            var unmarshaller = createUnmarshaller(handler);
            unmarshaller.unmarshal(xmlFile, type);
        } catch (JAXBException ex) {
            return getOrThrowError(ex);
        }
        return handler.getErrors();
    }

    @Override
    public List<IValidationError> validate(Document xmlDocument) {
        var handler = new FailSafeHandler(false);
        try {
            var unmarshaller = createUnmarshaller(handler);
            unmarshaller.unmarshal(xmlDocument, type);
        } catch (JAXBException ex) {
            return getOrThrowError(ex);
        }
        return handler.getErrors();
    }

    @Override
    public List<IValidationError> validate(I invoice) {
        var handler = new FailSafeHandler(false);
        try {
            var document = createDocument();
            var marshaller = createMarshaller(handler, InvoiceWriter.NS_PREFIX_MAPPER);
            var root = createJAXBElement(invoice);
            marshaller.marshal(root, document);
        } catch (JAXBException ex) {
            return getOrThrowError(ex);
        } catch (ParserConfigurationException e) {
            throw new XConfigurationException("Cannot create XML document", e);
        }
        return handler.getErrors();
    }

}
