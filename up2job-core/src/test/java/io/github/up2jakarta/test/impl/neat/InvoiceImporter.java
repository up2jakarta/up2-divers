package io.github.up2jakarta.test.impl.neat;

import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;

public class InvoiceImporter extends NeatImporter<TermType, SegmentType, Invoice, InputRecord, InputError> {

    public InvoiceImporter(Up2Factory<TermType> factory) throws BeanException {
        super(factory, Invoice.class, SegmentType.class);
    }

    @Override
    protected InputHandler.Builder newBuilder(int size) {
        return new InputHandler.Builder(size);
    }

}

