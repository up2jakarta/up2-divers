package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.io.MessFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

public class InvoiceMessWriter extends MessFileWriter<Invoice> {

    public InvoiceMessWriter(InvoiceMessImporter importer, CSVFormat format) throws BeanException {
        super(importer.toExporter(), format);
    }

}
