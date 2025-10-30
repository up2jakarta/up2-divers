package io.github.up2jakarta.job.csv.impl.fast;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.FastImporter;
import io.github.up2jakarta.job.csv.dto.Invoice;
import io.github.up2jakarta.job.csv.impl.GroupType;
import io.github.up2jakarta.job.csv.impl.SegmentType;

import static io.github.up2jakarta.job.csv.impl.SegmentType.S01;

public class InvoiceImporter extends FastImporter<Invoice, GroupType, SegmentType, InputRecord, InputError> {

    public InvoiceImporter(Up2Factory<GroupType> factory) throws BeanException {
        super(factory, Invoice.class, S01, SegmentType.values());
    }

    @Override
    protected InputHandler create(InputRecord row) {
        return new InputHandler(row);
    }

}

