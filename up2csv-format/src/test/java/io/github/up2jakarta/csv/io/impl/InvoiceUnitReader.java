package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.io.UnitFileReader;
import io.github.up2jakarta.csv.io.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

import static io.github.up2jakarta.csv.io.misc.Tests.TUError;
import static io.github.up2jakarta.csv.io.misc.Tests.TURecord;

public class InvoiceUnitReader extends UnitFileReader<Invoice, GroupType, SegmentType, TURecord, TUError> {

    public InvoiceUnitReader(InvoiceUnitImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected TURecord create(SegmentType type, String[] data) {
        return new TURecord(type, null, data);
    }

}

