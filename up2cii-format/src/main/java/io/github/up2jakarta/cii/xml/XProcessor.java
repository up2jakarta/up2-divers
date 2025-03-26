package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.api.XValidationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import org.xml.sax.SAXParseException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Common XML invoice's processor that is used in XML-Reader &amp; XML-Writer &amp; XSD-Validator.
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

    protected static StreamSource newStreamSource(File xmlFile) throws IOException {
        if (!xmlFile.exists() || !xmlFile.canRead()) {
            throw new FileNotFoundException(xmlFile.getPath());
        }
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

    protected static AbstractCollector newHandler(boolean failFast, boolean lenient) {
        if (failFast) {
            if (lenient) {
                return FailFastHandler.LENIENT_INSTANCE;
            }
            return FailFastHandler.STRICT_INSTANCE;
        }
        return new FailSafeHandler(lenient);
    }

    protected Unmarshaller newUnmarshaller(AbstractCollector handler) throws JAXBException {
        var unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(handler);
        Stream.of(adapters).forEach(unmarshaller::setAdapter);
        return unmarshaller;
    }

    protected Marshaller newMarshaller(AbstractCollector handler, NamespacePrefixMapper mapper) throws JAXBException {
        var marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setEventHandler(handler);
        marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", mapper);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Stream.of(adapters).forEach(marshaller::setAdapter);
        return marshaller;
    }

}
