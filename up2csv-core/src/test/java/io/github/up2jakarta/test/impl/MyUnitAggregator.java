package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.dto.Invoice;

public class MyUnitAggregator extends UnitImporter<TermType, SegmentType, Invoice, InputRecord, InputError> {

    public MyUnitAggregator(Up2Factory<TermType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.class);
    }

    @Override
    protected InputCollector.Builder newBuilder(int size) {
        return new InputCollector.Builder(size);
    }

}

