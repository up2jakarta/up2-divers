package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.misc.InputError;
import io.github.up2jakarta.csv.io.misc.InputHandler;
import io.github.up2jakarta.csv.io.misc.InputRecord;
import io.github.up2jakarta.lov.core.BeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceFullImporter extends FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> {

    @Autowired
    public InvoiceFullImporter(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01);
    }

    @Override
    protected InputHandler.Builder newBuilder(int size) {
        return new InputHandler.Builder(size);
    }

}

