package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.dto.Invoice;

public class MyFastAggregator extends FastImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> {

    public MyFastAggregator(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01);
    }

    @Override
    protected InputCollector.Builder newBuilder(int size) {
        return new InputCollector.Builder(size);
    }

}

