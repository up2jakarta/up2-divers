package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.ops.FastAggregator;

public class InvoiceAggregator extends FastAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> {

    private final SimpleCreator creator;

    public InvoiceAggregator(MapperFactory<GroupType> factory, SimpleCreator creator) throws BeanException {
        super(factory, Invoice.class, SegmentType.S01, SegmentType.values());
        this.creator = creator;
    }

    @Override
    protected SimpleHandler create(InputRowEntity row) {
        return new SimpleHandler(row, creator);
    }

}

