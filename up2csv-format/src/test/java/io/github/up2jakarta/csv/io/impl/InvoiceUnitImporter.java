package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.UnitImporter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.misc.MyError;
import io.github.up2jakarta.csv.io.misc.MyHandler;
import io.github.up2jakarta.csv.io.misc.MyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceUnitImporter extends UnitImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> {

    @Autowired
    public InvoiceUnitImporter(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01, SegmentType.values());
    }

    @Override
    protected MyHandler create(MyRecord row) {
        return new MyHandler(row);
    }

}

