package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.impl.dto.Invoice;

public class MyFullAggregator extends FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> {

    public MyFullAggregator(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01, SegmentType.values());
    }

    @Override
    protected InputCollector create(InputRecord row) {
        return new InputCollector(row);
    }

}

