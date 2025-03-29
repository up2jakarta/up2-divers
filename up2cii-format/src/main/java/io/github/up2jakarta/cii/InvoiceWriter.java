package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.core.ErrorEnhancer;
import io.github.up2jakarta.xml.XBuilder;
import io.github.up2jakarta.xml.api.XConfigurationException;
import io.github.up2jakarta.xml.api.XWriter;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.Writer;

import static io.github.up2jakarta.cii.CII.*;

/**
 * Thread-safe processor that write CII-D16B invoices.
 */
public class InvoiceWriter<I> extends XBuilder<I> implements XWriter<I> {

    public InvoiceWriter(final Class<I> type, final XmlAdapter<?, ?>... adapters) {
        super(CII_QNAME, type, getBuilder(), getSchema(), adapters);
    }

    @Override
    public Document write(I invoice, boolean failFast) {
        try {
            var document = newDocument();
            var handler = newHandler(ErrorEnhancer::enhance, failFast, false);
            var marshaller = newMarshaller(handler, CII::config);
            var root = newElement(invoice);
            marshaller.marshal(root, document);
            handler.throwValidationExceptionWhenError();
            return document;
        } catch (JAXBException ex) {
            throw newException(ex);
        } catch (ParserConfigurationException e) {
            throw new XConfigurationException("Cannot create XML document", e);
        }
    }

    @Override
    public void write(I invoice, File xmlfile, boolean failFast) {
        try {
            var handler = newHandler(ErrorEnhancer::enhance, failFast, false);
            var marshaller = newMarshaller(handler, CII::config);
            var root = newElement(invoice);
            marshaller.marshal(root, xmlfile);
            handler.throwValidationExceptionWhenError();
        } catch (JAXBException ex) {
            throw newException(ex);
        }
    }

    @Override
    public void write(I invoice, Writer writer, boolean failFast) {
        try {
            var handler = newHandler(ErrorEnhancer::enhance, failFast, false);
            var marshaller = newMarshaller(handler, CII::config);
            var root = newElement(invoice);
            marshaller.marshal(root, writer);
            handler.throwValidationExceptionWhenError();
        } catch (JAXBException ex) {
            throw newException(ex);
        }
    }

}
