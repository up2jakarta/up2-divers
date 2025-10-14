package io.github.up2jakarta.csv.ops.misc;

import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.impl.SegmentType;

import static io.github.up2jakarta.csv.ops.ModeType.FULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AFullTester<R extends IRecordEntity<SegmentType, ?, ?>> extends AFastTester<R> {

    public AFullTester(BusinessObject invoice, R[] output) {
        super(FULL, invoice, output);
    }

    protected void assertFound(final R row, final String[] data) {
        super.assertFound(row, data);
        assertNotNull(data[0]);
        assertEquals(row.getType().getCode(), data[FULL.getTypeIdIndex()]);
        assertEquals(row.getBusinessReference(), data[FULL.getBeanIdIndex()]);
    }

}
