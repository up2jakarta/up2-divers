package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.io.UnitFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

public class InvoiceUnitWriter extends UnitFileWriter<Invoice> {

    public InvoiceUnitWriter(InvoiceUnitImporter importer, CSVFormat format) throws BeanException {
        super(importer.toExporter(), format);
    }

}
