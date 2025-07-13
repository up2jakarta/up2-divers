package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessId;
import io.github.up2jakarta.job.core.BusinessLoader;
import io.github.up2jakarta.job.flux.FluxSupplier;
import io.github.up2jakarta.xml.XContext;
import io.github.up2jakarta.xml.api.XConfigurationException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.Optional;
import java.util.zip.ZipEntry;

import static javax.xml.xpath.XPathConstants.STRING;

@SuppressWarnings("unused")
public class EntryDeepDecoder<C extends BusinessContext> implements EntryDecoder {

    private final DocumentBuilderFactory documentFactory;
    private final XPathExpression expression;
    private final BusinessLoader<C> loader;
    private final C context;

    public EntryDeepDecoder(C context, BusinessLoader<C> loader, String referencePath) {
        this.documentFactory = XContext.newDocumentFactory(null, false, false);
        this.context = context;
        this.loader = loader;
        try {
            this.expression = XPathFactory.newDefaultInstance().newXPath().compile(referencePath);
        } catch (XPathExpressionException e) {
            throw new XConfigurationException("Cannot configure XPath", e);
        }
    }

    @Override
    public Optional<BusinessId> decode(ZipEntry entry, FluxSupplier<InputStream> input) {
        try (final InputStream fileIS = input.get()) {
            final Document doc = documentFactory.newDocumentBuilder().parse(fileIS);
            final String reference = (String) expression.evaluate(doc, STRING);
            final long id = loader.get(context, reference);
            return Optional.of(new BusinessId(id, reference));
        } catch (Exception ignore) {
            return Optional.empty();
        }
    }

}
