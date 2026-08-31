package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.io.MessFileReader;
import io.github.up2jakarta.test.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

import static io.github.up2jakarta.test.misc.Tests.TUError;
import static io.github.up2jakarta.test.misc.Tests.TURecord;

public class InvoiceMessReader extends MessFileReader<Invoice, TermType, SegmentType, TURecord, TUError> {

    public InvoiceMessReader(InvoiceMessImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected TURecord create(SegmentType type, String invoiceNumber, String[] data) {
        return new TURecord(type, invoiceNumber, data);
    }

}

