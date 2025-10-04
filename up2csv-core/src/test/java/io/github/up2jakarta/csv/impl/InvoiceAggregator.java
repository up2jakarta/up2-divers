package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.BusinessAggregator;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.test.sample.Invoice;

public class InvoiceAggregator extends BusinessAggregator<Invoice, BusinessType, SegmentType, InputRowEntity, InputErrorEntity> {

    private final ErrorCreator creator;

    public InvoiceAggregator(MapperFactory<BusinessType> factory, ErrorCreator creator) throws BeanException {
        super(factory, SegmentType.S01, Invoice.class, 0);
        this.creator = creator;
    }

    @Override
    protected ErrorHandler newHandler(InputRowEntity row) {
        return new ErrorHandler(row, creator);
    }

}

