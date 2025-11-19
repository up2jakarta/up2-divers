package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.lov.core.BeanException;

public class MyUnitAggregator extends UnitImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> {

    public MyUnitAggregator(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01);
    }

    @Override
    protected InputCollector.Builder newBuilder(int size) {
        return new InputCollector.Builder(size);
    }

}

