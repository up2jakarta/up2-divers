package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.io.NeatFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

public class InvoiceNeatWriter extends NeatFileWriter<Invoice> {

    public InvoiceNeatWriter(InvoiceNeatImporter importer, CSVFormat format) throws BeanException {
        super(importer.toExporter(), format);
    }

}
