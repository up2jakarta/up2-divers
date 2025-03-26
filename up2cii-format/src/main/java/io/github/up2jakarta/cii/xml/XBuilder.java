package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.CII;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

/**
 * Common XML invoice's builder that is used in XML-Writer &amp; XSD-Validator.
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
        return new JAXBElement<>(CII.CII_QNAME, type, null, value);
    }

    @SuppressWarnings("java:S2755")
    protected Document newDocument() throws ParserConfigurationException {
        var db = this.newDocumentBuilder();
        var document = db.newDocument();
        document.setStrictErrorChecking(true);
        return document;
    }

    public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        return factory.newDocumentBuilder();
    }

}
