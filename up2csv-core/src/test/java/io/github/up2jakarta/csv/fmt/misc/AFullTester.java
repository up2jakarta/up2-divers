package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.impl.SegmentType;

import static io.github.up2jakarta.csv.core.ModeType.FULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AFullTester<R extends IRecordEntity<SegmentType, ?, ?>> extends AUnitTester<R> {

    private final String key;

    public AFullTester(Referencable invoice, R[] output) {
        super(FULL, output);
        this.key = invoice.getReference();
    }

    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getBeanIdIndex()]);
        assertNotNull(data[FULL.getRowKeyIndex()]);
        assertEquals(row.getType().getCode(), data[FULL.getTypeIdIndex()]);
        assertEquals(row.getBusinessReference(), data[FULL.getBeanIdIndex()]);
    }

}
