package io.github.up2jakarta.xml;

import io.github.up2jakarta.xml.api.AbstractCollector;
import io.github.up2jakarta.xml.api.MessageEnhancer;
import io.github.up2jakarta.xml.api.XConfigurationException;
import io.github.up2jakarta.xml.api.XValidationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;
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

    protected XProcessor(Class<X> type, Schema xsd, final XmlAdapter<?, ?>... adapters) {
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
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(handler);
        Stream.of(adapters).forEach(unmarshaller::setAdapter);
        return unmarshaller;
    }

    protected Marshaller newMarshaller(AbstractCollector handler, Consumer<Marshaller> customizer) throws JAXBException {
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setEventHandler(handler);
        customizer.accept(marshaller);
        Stream.of(adapters).forEach(marshaller::setAdapter);
        return marshaller;
    }

    protected Validator newValidator() {
        try {
            final Validator validator = schema.newValidator();
            validator.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, XContext.ALLOWED_PROTOCOL);
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, XContext.ALLOWED_PROTOCOL);
            return validator;
        } catch (SAXNotSupportedException | SAXNotRecognizedException e) {
            throw new XConfigurationException("Cannot create XML validator", e);
        }
    }

}
