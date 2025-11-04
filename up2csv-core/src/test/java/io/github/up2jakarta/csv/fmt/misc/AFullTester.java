package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.impl.SegmentType;

import static io.github.up2jakarta.csv.core.ModeType.FULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AFullTester<R extends IFullRecord<SegmentType>> extends AUnitTester<R> {

    private final String key;

    public AFullTester(Referencable invoice, R[] output) {
        super(FULL, output);
        this.key = invoice.getReference();
    }

    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getBeanIdIndex()]);
        assertEquals(key, row.getPivot());
        assertNotNull(data[mode.getRowKeyIndex()]);
        assertEquals(row.getType().getCode(), data[mode.getTypeIdIndex()]);
    }

}
