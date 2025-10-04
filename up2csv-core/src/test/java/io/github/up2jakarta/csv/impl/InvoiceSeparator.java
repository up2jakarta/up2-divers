package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.BusinessSeparator;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.test.sample.Invoice;

import java.util.function.Consumer;

public class InvoiceSeparator extends BusinessSeparator<Invoice, BusinessType, SegmentType> {

    public InvoiceSeparator(MapperFactory<BusinessType> factory) throws BeanException {
        super(factory, SegmentType.S01, Invoice.class, Invoice::getReference);
    }

    public final void format(Invoice bean, Consumer<String[]> callback) throws BeanException {
        super.format(bean, 0, 1, callback);
    }

}

