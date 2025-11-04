package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.misc.InputError;
import io.github.up2jakarta.csv.io.misc.InputHandler;
import io.github.up2jakarta.csv.io.misc.InputRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceFullImporter extends FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> {

    @Autowired
    public InvoiceFullImporter(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01, SegmentType.values());
    }

    @Override
    protected InputHandler create(InputRecord row) {
        return new InputHandler(row);
    }

}

