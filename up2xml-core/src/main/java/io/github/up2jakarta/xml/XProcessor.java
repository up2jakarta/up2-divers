package io.github.up2jakarta.xml;

import io.github.up2jakarta.xml.api.AbstractCollector;
import io.github.up2jakarta.xml.api.MessageEnhancer;
import io.github.up2jakarta.xml.api.XPrefixMapper;
import io.github.up2jakarta.xml.api.XValidationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.xml.sax.SAXParseException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Common XML processor that is used in XML-Reader &amp; XML-Writer &amp; XSD-Validator.
 *
 * @param <X> the XML Type
 */
public abstract class XProcessor<X> {

    protected final Class<X> type;
    protected final Schema schema;
    private final JAXBContext context;
    private final XmlAdapter<?, ?>[] adapters;

    protected XProcessor(Class<X> type, Schema xsd, final XmlAdapter<?, ?>[] adapters) {
        this.type = type;
        this.context = XContext.getContext(type);
        this.schema = xsd;
        this.adapters = adapters;
    }

    public static void assertReadable(File xmlFile) throws FileNotFoundException {
        if (!xmlFile.exists() || !xmlFile.canRead()) {
            throw new FileNotFoundException(xmlFile.getPath());
        }
    }

    protected static StreamSource newStreamSource(File xmlFile) throws IOException {
        assertReadable(xmlFile);
        try {
            return new StreamSource(xmlFile);
        } catch (Exception e) {
            throw new IOException("Cannot stream XML file: " + xmlFile, e);
        }
    }

    protected static XValidationException newException(JAXBException ex) {
        if (ex.getLinkedException() instanceof SAXParseException) {
            return new XValidationException(ex.getLinkedException());
        }
        return new XValidationException(ex);
    }

    protected static AbstractCollector newHandler(MessageEnhancer enhancer, boolean failFast, boolean lenient) {
        if (failFast) {
            if (lenient) {
                return FailFastHandler.LENIENT_INSTANCE;
            }
            return FailFastHandler.STRICT_INSTANCE;
        }
        return new FailSafeHandler(enhancer, lenient);
    }

    protected Unmarshaller newUnmarshaller(AbstractCollector handler) throws JAXBException {
        var unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(handler);
        Stream.of(adapters).forEach(unmarshaller::setAdapter);
        return unmarshaller;
    }

    protected Marshaller newMarshaller(AbstractCollector handler, XPrefixMapper prefixMapper) throws JAXBException {
        var marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setEventHandler(handler);
        marshaller.setProperty(prefixMapper.getProperty(), prefixMapper);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Stream.of(adapters).forEach(marshaller::setAdapter);
        return marshaller;
    }

}
