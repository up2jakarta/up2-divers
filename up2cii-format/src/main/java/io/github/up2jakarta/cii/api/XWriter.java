package io.github.up2jakarta.cii.api;

import org.w3c.dom.Document;

import java.io.File;
import java.io.Writer;

/**
 * Common writer for XML invoices.
 *
 * @param <X> the XML Type
 */
public interface XWriter<X> {

    /**
     * Write the given {@param invoice} into an XML document.
     *
     * @param invoice  the input to be written.
     * @param failFast specify that the writer should fail at the first error detected or collect the maximum of errors.
     * @return a valid XML Document.
     */
    Document write(X invoice, boolean failFast);

    /**
     * Write the given {@param invoice} into an XML file.
     *
     * @param invoice  the input to be written.
     * @param path     the output path.
     * @param failFast specify that the writer should fail at the first error detected or collect the maximum of errors.
     */
    void write(X invoice, File path, boolean failFast);

    /**
     * Write the given {@param invoice} into an XML file.
     *
     * @param invoice  the input to be written.
     * @param writer   the output writer.
     * @param failFast specify that the writer should fail at the first error detected or collect the maximum of errors.
     */
    void write(X invoice, Writer writer, boolean failFast);
}
