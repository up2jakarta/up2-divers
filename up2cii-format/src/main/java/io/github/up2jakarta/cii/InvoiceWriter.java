package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.api.XConfigurationException;
import io.github.up2jakarta.cii.api.XWriter;
import io.github.up2jakarta.cii.xml.XProcessor;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.Writer;

import static io.github.up2jakarta.cii.CII.XSD_URL;

/**
 * Thread-safe processor that write CII-D16B invoices.
 */
public class InvoiceWriter<I> extends XProcessor<I> implements XWriter<I> {

    static final NamespacePrefixMapper NS_PREFIX_MAPPER = CII.NS_PREFIX_MAPPER;

    public InvoiceWriter(Class<I> type, final XmlAdapter<?, ?>[] adapters) {
        super(type, XSD_URL, adapters);
    }

    @Override
    public Document write(I invoice, boolean failFast) {
        try {
            var document = createDocument();
            var handler = computeHandler(failFast, false);
            var marshaller = createMarshaller(handler, NS_PREFIX_MAPPER);
            var root = createJAXBElement(invoice);
            marshaller.marshal(root, document);
            handler.throwValidationExceptionWhenError();
            return document;
        } catch (JAXBException ex) {
            throw createException(ex);
        } catch (ParserConfigurationException e) {
            throw new XConfigurationException("Cannot create XML document", e);
        }
    }

    @Override
    public void write(I invoice, File xmlfile, boolean failFast) {
        try {
            var handler = computeHandler(failFast, false);
            var marshaller = createMarshaller(handler, NS_PREFIX_MAPPER);
            var root = createJAXBElement(invoice);
            marshaller.marshal(root, xmlfile);
            handler.throwValidationExceptionWhenError();
        } catch (JAXBException ex) {
            throw createException(ex);
        }
    }

    @Override
    public void write(I invoice, Writer writer, boolean failFast) {
        try {
            var handler = computeHandler(failFast, false);
            var marshaller = createMarshaller(handler, NS_PREFIX_MAPPER);
            var root = createJAXBElement(invoice);
            marshaller.marshal(root, writer);
            handler.throwValidationExceptionWhenError();
        } catch (JAXBException ex) {
            throw createException(ex);
        }
    }

}
