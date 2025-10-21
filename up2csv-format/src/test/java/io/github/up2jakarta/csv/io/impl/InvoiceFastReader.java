package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.io.FastFileReader;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.misc.MyError;
import io.github.up2jakarta.csv.io.misc.MyRecord;
import org.apache.commons.csv.CSVFormat;

public class InvoiceFastReader extends FastFileReader<Invoice, GroupType, SegmentType, MyRecord, MyError> {

    public InvoiceFastReader(InvoiceFastImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected MyRecord create(SegmentType type, String invoiceNumber, String[] data) {
        return new MyRecord(type, invoiceNumber, data);
    }

}

