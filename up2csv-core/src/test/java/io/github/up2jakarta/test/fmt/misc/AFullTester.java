package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;

import static io.github.up2jakarta.csv.core.ModeType.FULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AFullTester<R extends IFullRecord<SegmentType, ?>> extends AUnitTester<R> {

    private final String key;

    public AFullTester(Invoice invoice, R[] output) {
        super(FULL, output);
        this.key = invoice.getReference();
    }

    @Override
    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getBeanIdIndex()]);
        assertEquals(key, row.getPivot());
        assertNotNull(data[0]);
        assertEquals(row.getType().getCode(), data[mode.getTypeIdIndex()]);
    }

}
