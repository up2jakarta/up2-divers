package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.api.XConfigurationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.up2jakarta.cii.CII.CII_ALLOWED_PROTOCOL;
import static javax.xml.XMLConstants.*;

/**
 * Cache Factory for XML objects like {@link JAXBContext} &amp; {@link Schema}.
 */
class XContext {

    private static final Map<Class<?>, JAXBContext> CACHE_CONTEXT = new ConcurrentHashMap<>();
    private static final Map<String, Schema> CACHE_SCHEMA = new ConcurrentHashMap<>();
    private static final Map<String, DocumentBuilderFactory> CACHE_FACTORY = new ConcurrentHashMap<>();

    public static JAXBContext getContext(final Class<?> type) {
        return CACHE_CONTEXT.computeIfAbsent(type, (key) -> {
            try {
                return JAXBContext.newInstance(key);
            } catch (JAXBException e) {
                throw new XConfigurationException("Cannot create XML context", e);
            }
        });
    }

    public static Schema getSchema(final URL xsd) {
        return CACHE_SCHEMA.computeIfAbsent(xsd.toExternalForm(), (key) -> {
            try {
                var factory = SchemaFactory.newDefaultInstance();
                factory.setFeature(FEATURE_SECURE_PROCESSING, true);
                factory.setProperty(ACCESS_EXTERNAL_SCHEMA, CII_ALLOWED_PROTOCOL);
                factory.setProperty(ACCESS_EXTERNAL_DTD, CII_ALLOWED_PROTOCOL);
                return factory.newSchema(xsd);
            } catch (SAXException e) {
                throw new XConfigurationException("Cannot parse XML schema", e);
            }
        });
    }

    public static DocumentBuilderFactory getDocumentBuilderFactory(final URL xsd, final Schema schema) {
        return CACHE_FACTORY.computeIfAbsent(xsd.toExternalForm(), (key) -> {
            try {
                var dbf = DocumentBuilderFactory.newDefaultInstance();
                dbf.setFeature(FEATURE_SECURE_PROCESSING, true);
                dbf.setAttribute(ACCESS_EXTERNAL_DTD, CII_ALLOWED_PROTOCOL);
                dbf.setAttribute(ACCESS_EXTERNAL_SCHEMA, CII_ALLOWED_PROTOCOL);
                dbf.setSchema(schema);
                dbf.setValidating(false);
                dbf.setNamespaceAware(true);
                return dbf;
            } catch (ParserConfigurationException e) {
                throw new XConfigurationException("Cannot secure XML factory", e);
            }
        });
    }
}
