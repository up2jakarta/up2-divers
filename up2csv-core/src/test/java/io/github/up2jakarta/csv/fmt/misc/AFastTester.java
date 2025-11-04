package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.impl.SegmentType;

import static io.github.up2jakarta.csv.core.ModeType.FAST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class AFastTester<R extends IFastRecord<SegmentType>> extends AUnitTester<R> {

    private final String key;

    public AFastTester(Referencable invoice, R[] output) {
        super(FAST, output);
        this.key = invoice.getReference();
    }

    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getBeanIdIndex()]);
        assertEquals(key, row.getPivot());
    }

}
