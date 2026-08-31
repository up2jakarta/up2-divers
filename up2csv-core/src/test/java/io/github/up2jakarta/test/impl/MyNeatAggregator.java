package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.dto.Invoice;

public class MyNeatAggregator extends NeatImporter<TermType, SegmentType, Invoice, InputRecord, InputError> {

    public MyNeatAggregator(Up2Factory<TermType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.class);
    }

    @Override
    protected InputCollector.Builder newBuilder(int size) {
        return new InputCollector.Builder(size);
    }

}

