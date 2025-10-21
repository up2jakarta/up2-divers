package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.io.FastFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

public class InvoiceFastWriter extends FastFileWriter<Invoice> {

    public InvoiceFastWriter(InvoiceFastImporter importer, CSVFormat format) throws BeanException {
        super(importer.toExporter(), format);
    }

}
