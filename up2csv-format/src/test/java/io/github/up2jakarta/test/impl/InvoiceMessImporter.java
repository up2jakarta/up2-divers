package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.github.up2jakarta.test.misc.Tests.*;

@Service
public class InvoiceMessImporter extends MessImporter<TermType, SegmentType, Invoice, TURecord, TUError> {

    @Autowired
    public InvoiceMessImporter(Up2Factory<TermType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.class);
    }

    @Override
    protected TUHandler.Builder newBuilder(int size) {
        return new TUHandler.Builder(size);
    }

}

