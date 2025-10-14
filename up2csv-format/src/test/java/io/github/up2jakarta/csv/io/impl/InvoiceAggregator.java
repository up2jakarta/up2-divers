package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.SimpleAggregator;
import io.github.up2jakarta.csv.io.dto.Invoice;

public class InvoiceAggregator extends SimpleAggregator<Invoice, GroupType, SegmentType> {

    public InvoiceAggregator(MapperFactory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01, SegmentType.values());
    }
}

