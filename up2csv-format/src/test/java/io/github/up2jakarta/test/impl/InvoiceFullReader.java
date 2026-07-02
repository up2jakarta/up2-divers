package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.io.FullFileReader;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.misc.InputError;
import io.github.up2jakarta.test.misc.InputRecord;
import org.apache.commons.csv.CSVFormat;

public class InvoiceFullReader extends FullFileReader<Invoice, TermType, SegmentType, InputRecord, InputError> {

    public InvoiceFullReader(InvoiceFullImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected InputRecord create(long lineId, String recordKey, SegmentType type, String beanKey, String[] data) {
        return new InputRecord(recordKey, type, beanKey, data);
    }

}

