package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.UnitImporter;
import io.github.up2jakarta.csv.impl.dto.Invoice;

public class MyUnitAggregator extends UnitImporter<Invoice, GroupType, SegmentType, InputRecord, InputError> {

    public MyUnitAggregator(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01, SegmentType.values());
    }

    @Override
    protected InputCollector create(InputRecord row) {
        return new InputCollector(row);
    }

}

