package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.io.FullFileReader;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.misc.InputError;
import io.github.up2jakarta.csv.io.misc.InputRecord;
import io.github.up2jakarta.xml.adapters.KeyCoder;
import org.apache.commons.csv.CSVFormat;

public class InvoiceFullReader extends FullFileReader<Invoice, GroupType, SegmentType, InputRecord, InputError> {

    public InvoiceFullReader(InvoiceFullImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected InputRecord create(long lineId, String recordKey, SegmentType type, String beanKey, String[] data) {
        if (recordKey == null) {
            recordKey = KeyCoder.fixed(lineId);
        }
        return new InputRecord(recordKey, type, beanKey, data);
    }

}

