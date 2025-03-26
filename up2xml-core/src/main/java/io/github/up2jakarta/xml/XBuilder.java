package io.github.up2jakarta.xml;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.IOException;

/**
 * Common XML DOM builder that is used in XML-Writer &amp; XSD-Validator.
 *
 * @param <X> the XML Type
 */
public abstract class XBuilder<X> extends XProcessor<X> {

    protected final QName name;
    protected final DocumentBuilderFactory factory;

    public XBuilder(QName name, Class<X> type, boolean validating, Schema xsd, final XmlAdapter<?, ?>[] adapters) {
        super(type, xsd, adapters);
        this.factory = XContext.getDocumentBuilderFactory(xsd, validating);
        this.name = name;
    }

    protected JAXBElement<X> newElement(X value) {
        return new JAXBElement<>(name, type, null, value);
    }

    @SuppressWarnings("java:S2755")
    protected Document newDocument() throws ParserConfigurationException {
        final DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.newDocument();
        document.setStrictErrorChecking(true);
        return document;
    }

    public Document newDocument(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        final InputSource xmlSource = new InputSource(xmlFile.toURI().toASCIIString());
        return newDocument(xmlSource);
    }

    public Document newDocument(InputSource xmlSource) throws ParserConfigurationException, IOException, SAXException {
        return factory.newDocumentBuilder().parse(xmlSource);
    }

}
