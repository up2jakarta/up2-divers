package io.github.up2jakarta.cii.api;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;

/**
 * Common reader for XML invoices.
 *
 * @param <X> the XML Type
 */
public interface XReader<X> {

    /**
     * Read the given {@param xmlFile} into {@link X}.
     *
     * @param xmlFile  the input to read: Must be an XML document and must not be null.
     * @param failFast specify that the reader should fail at the first error detected or collect the maximum of errors.
     * @return the parsed {@link X}
     * @throws IOException if the underlying {@link org.xml.sax.XMLReader} throws an {@link IOException}.
     */
    default X read(File xmlFile, boolean failFast) throws IOException {
        return read(xmlFile, failFast, failFast);
    }

    /**
     * Read the given {@param xmlDocument} into {@link X}.
     *
     * @param xmlDocument the input to read: Must be not null.
     * @param failFast    specify that the reader should fail at the first error detected or collect the maximum of errors.
     * @return the parsed {@link X}
     */
    default X read(Document xmlDocument, boolean failFast) {
        return read(xmlDocument, failFast, failFast);
    }

    /**
     * Read the given {@param xmlFile} into {@link X}.
     *
     * @param xmlFile  the input to read: Must be an XML document and must not be null.
     * @param failFast specify that the reader should fail at the first error detected or collect the maximum of errors.
     * @param lenient  specify that the reader should ignore warnings and <code>Unexpected element</code>.
     * @return the parsed {@link X}
     * @throws IOException if the underlying {@link org.xml.sax.XMLReader} throws an {@link IOException}.
     */
    X read(File xmlFile, boolean failFast, boolean lenient) throws IOException;

    /**
     * Read the given {@param xmlFile} into {@link X}.
     *
     * @param xmlFile  the input to read: Must be not null.
     * @param failFast specify that the reader should fail at the first error detected or collect the maximum of errors.
     * @param lenient  specify that the reader should ignore warnings and <code>Unexpected element</code>.
     * @return the parsed {@link X}
     */
    X read(Document xmlFile, boolean failFast, boolean lenient);

}
