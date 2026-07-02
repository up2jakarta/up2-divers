package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.flux.FluxSupplier;
import io.github.up2jakarta.xml.XContext;
import io.github.up2jakarta.xml.api.XConfigurationException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.zip.ZipEntry;

import static io.github.up2jakarta.job.core.ResourceAware.XML_EXTENSION;
import static javax.xml.xpath.XPathConstants.STRING;

public class XPathDecoder implements EntryDecoder {

    private final DocumentBuilderFactory documentFactory;
    private final XPathExpression expression;

    public XPathDecoder(String Path) {
        this.documentFactory = XContext.newDocumentFactory(null, false, false);
        try {
            this.expression = XPathFactory.newDefaultInstance().newXPath().compile(Path);
        } catch (XPathExpressionException e) {
            throw new XConfigurationException("Cannot configure XPath", e);
        }
    }

    @Override
    public String encode(String reference) {
        return reference + XML_EXTENSION;
    }

    @Override
    public String decode(ZipEntry entry, FluxSupplier<InputStream> input) throws Exception {
        try (final InputStream fis = input.get()) {
            final Document doc = documentFactory.newDocumentBuilder().parse(fis);
            return (String) expression.evaluate(doc, STRING);
        }
    }

}
