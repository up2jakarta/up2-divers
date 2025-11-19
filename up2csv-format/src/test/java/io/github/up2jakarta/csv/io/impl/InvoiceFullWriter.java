package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.fmt.Fixed06Generator;
import io.github.up2jakarta.csv.io.FullFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;

public class InvoiceFullWriter extends FullFileWriter<Invoice> {

    public InvoiceFullWriter(InvoiceFullImporter importer, CSVFormat format) throws BeanException {
        super(importer.toExporter(), format, new Fixed06Generator());
    }

}
