package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.test.fmt.tree.Tree90;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;

import static io.github.up2jakarta.csv.core.IMode.FULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AFullTester<R extends IFullRecord<SegmentType>> extends ANeatTester<R> {

    private final String key;

    public AFullTester(Invoice invoice, R[] output) {
        super(FULL, output);
        this.key = invoice.getReference();
    }

    public AFullTester(Tree90 root, R[] output) {
        super(FULL, output);
        this.key = root.getKey();
    }

    @Override
    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getOffset()]);
        assertEquals(key, row.getPivot());
        assertNotNull(data[0]);
        assertEquals(row.getType().getCode(), data[mode.getIndex()]);
    }

}
