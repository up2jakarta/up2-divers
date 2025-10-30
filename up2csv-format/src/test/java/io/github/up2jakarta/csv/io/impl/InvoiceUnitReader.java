package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.io.UnitFileReader;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.misc.MyError;
import io.github.up2jakarta.csv.io.misc.MyRecord;
import org.apache.commons.csv.CSVFormat;

public class InvoiceUnitReader extends UnitFileReader<Invoice, GroupType, SegmentType, MyRecord, MyError> {

    public InvoiceUnitReader(InvoiceUnitImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected MyRecord create(SegmentType type, String[] data) {
        return new MyRecord(type, null, data);
    }

}

