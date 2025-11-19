package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.io.UnitFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;

public class InvoiceUnitWriter extends UnitFileWriter<Invoice> {

    public InvoiceUnitWriter(InvoiceUnitImporter importer, CSVFormat format) throws BeanException {
        super(importer.toExporter(), format);
    }

}
