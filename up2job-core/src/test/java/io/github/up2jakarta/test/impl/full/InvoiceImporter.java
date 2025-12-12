package io.github.up2jakarta.test.impl.full;

import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.SegmentType;

import static io.github.up2jakarta.test.impl.SegmentType.S01;

public class InvoiceImporter extends FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> {

    public InvoiceImporter(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, S01);
    }

    @Override
    protected InputHandler.Builder newBuilder(int size) {
        return new InputHandler.Builder(size);
    }

}

