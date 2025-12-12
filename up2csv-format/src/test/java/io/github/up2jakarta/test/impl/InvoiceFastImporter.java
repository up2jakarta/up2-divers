package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.github.up2jakarta.test.misc.Tests.*;

@Service
public class InvoiceFastImporter extends FastImporter<GroupType, SegmentType, Invoice, TURecord, TUError> {

    @Autowired
    public InvoiceFastImporter(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01);
    }

    @Override
    protected TUHandler.Builder newBuilder(int size) {
        return new TUHandler.Builder(size);
    }

}

