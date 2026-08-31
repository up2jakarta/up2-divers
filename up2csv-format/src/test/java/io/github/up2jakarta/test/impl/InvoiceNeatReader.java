package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.io.NeatFileReader;
import io.github.up2jakarta.test.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

import static io.github.up2jakarta.test.misc.Tests.TUError;
import static io.github.up2jakarta.test.misc.Tests.TURecord;

public class InvoiceNeatReader extends NeatFileReader<Invoice, TermType, SegmentType, TURecord, TUError> {

    public InvoiceNeatReader(InvoiceNeatImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected TURecord create(SegmentType type, String[] data) {
        return new TURecord(type, null, data);
    }

}

