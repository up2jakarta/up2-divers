package io.github.up2jakarta.job.csv.impl.unit;

import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.job.csv.dto.Invoice;
import io.github.up2jakarta.job.csv.impl.GroupType;
import io.github.up2jakarta.job.csv.impl.SegmentType;
import io.github.up2jakarta.lov.core.BeanException;

import static io.github.up2jakarta.job.csv.impl.SegmentType.S01;

public class InvoiceImporter extends UnitImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> {

    public InvoiceImporter(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, S01);
    }

    @Override
    protected InputHandler.Builder newBuilder(int size) {
        return new InputHandler.Builder(size);
    }

}

