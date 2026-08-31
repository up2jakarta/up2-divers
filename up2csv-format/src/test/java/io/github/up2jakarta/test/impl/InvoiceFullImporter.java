package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.FullImporter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.misc.InputError;
import io.github.up2jakarta.test.misc.InputHandler;
import io.github.up2jakarta.test.misc.InputRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceFullImporter extends FullImporter<TermType, SegmentType, Invoice, InputRecord, InputError> {

    @Autowired
    public InvoiceFullImporter(Up2Factory<TermType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.class);
    }

    @Override
    protected InputHandler.Builder newBuilder(int size) {
        return new InputHandler.Builder(size);
    }

}

