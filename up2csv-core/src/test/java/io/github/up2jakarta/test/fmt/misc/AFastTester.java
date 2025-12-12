package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;

import static io.github.up2jakarta.csv.core.ModeType.FAST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class AFastTester<R extends IFastRecord<SegmentType, ?>> extends AUnitTester<R> {

    private final String key;

    public AFastTester(Invoice invoice, R[] output) {
        super(FAST, output);
        this.key = invoice.getReference();
    }

    @Override
    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getBeanIdIndex()]);
        assertEquals(key, row.getPivot());
    }

}
