package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.core.ErrorEnhancer;
import io.github.up2jakarta.xml.XProcessor;
import io.github.up2jakarta.xml.api.XReader;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

/**
 * Thread-safe processor that read CII-D16B invoice.
 */
public class InvoiceReader<I> extends XProcessor<I> implements XReader<I> {

    public InvoiceReader(Class<I> type, final XmlAdapter<?, ?>... adapters) {
        super(type, CII.getSchema(), adapters);
    }

    @Override
    public I read(StreamSource xmlFile, boolean failFast, boolean lenient) throws IOException {
        try {
            var handler = newHandler(ErrorEnhancer::enhance, failFast, lenient);
            var unmarshaller = newUnmarshaller(handler);
            var invoice = unmarshaller.unmarshal(xmlFile, type).getValue();
            handler.throwValidationExceptionWhenError();
            return invoice;
        } catch (JAXBException ex) {
            throw newException(ex);
        }
    }

    @Override
    public I read(File xmlFile, boolean failFast, boolean lenient) throws IOException {
        return this.read(newStreamSource(xmlFile), failFast, lenient);
    }

    @Override
    public I read(Document xmlDocument, boolean failFast, boolean lenient) {
        try {
            var handler = newHandler(ErrorEnhancer::enhance, failFast, lenient);
            var unmarshaller = newUnmarshaller(handler);
            var invoice = unmarshaller.unmarshal(xmlDocument, type).getValue();
            handler.throwValidationExceptionWhenError();
            return invoice;
        } catch (JAXBException ex) {
            throw newException(ex);
        }
    }

}
