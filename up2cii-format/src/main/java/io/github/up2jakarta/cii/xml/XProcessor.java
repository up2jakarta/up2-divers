package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.CII;
import io.github.up2jakarta.cii.api.XValidationException;
import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

/**
 * Common XML invoice's processor that is used in XML-Reader &amp; XML-Writer &amp; XSD-Validator.
 *
 * @param <X> the XML Type
 */
public abstract class XProcessor<X> {

    protected final Class<X> type;
    protected final Schema schema;
    protected final DocumentBuilderFactory factory;
    private final JAXBContext context;
    private final XmlAdapter<?, ?>[] adapters;

    public XProcessor(Class<X> type, URL xsd, final XmlAdapter<?, ?>[] adapters) {
        this.type = type;
        this.context = XContext.getContext(type);
        this.schema = XContext.getSchema(xsd);
        this.factory = XContext.getDocumentBuilderFactory(xsd, schema);
        this.adapters = adapters;
    }

    public static void requireReadable(File xmlFile) throws FileNotFoundException {
        if (!xmlFile.exists() || !xmlFile.canRead()) {
            throw new FileNotFoundException(xmlFile.getPath());
        }
    }

    protected static StreamSource getStreamSource(File xmlFile) throws IOException {
        requireReadable(xmlFile);
        try {
            return new StreamSource(xmlFile);
        } catch (Exception e) {
            throw new IOException("Cannot stream XML file: " + xmlFile, e);
        }
    }

    protected static XValidationException createException(JAXBException ex) {
        if (ex.getLinkedException() instanceof SAXParseException) {
            return new XValidationException(ex.getLinkedException());
        }
        return new XValidationException(ex);
    }

    protected static AbstractCollector computeHandler(boolean failFast, boolean lenient) {
        if (failFast) {
            if (lenient) {
                return FailFastHandler.LENIENT_INSTANCE;
            }
            return FailFastHandler.STRICT_INSTANCE;
        }
        return new FailSafeHandler(lenient);
    }

    protected JAXBElement<X> createJAXBElement(X value) {
        return new JAXBElement<>(CII.CII_QNAME, type, null, value);
    }

    protected Unmarshaller createUnmarshaller(AbstractCollector handler) throws JAXBException {
        var unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(handler);
        Stream.of(adapters).forEach(unmarshaller::setAdapter);
        return unmarshaller;
    }

    protected Marshaller createMarshaller(AbstractCollector handler, NamespacePrefixMapper mapper) throws JAXBException {
        var marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setEventHandler(handler);
        marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", mapper);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Stream.of(adapters).forEach(marshaller::setAdapter);
        return marshaller;
    }

    @SuppressWarnings("java:S2755") // DOCUMENT_BUILDER_FACTORY is already secured
    protected Document createDocument() throws ParserConfigurationException {
        var db = this.getDocumentBuilder();
        var document = db.newDocument();
        document.setStrictErrorChecking(true);
        return document;
    }

    public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return factory.newDocumentBuilder();
    }

}
