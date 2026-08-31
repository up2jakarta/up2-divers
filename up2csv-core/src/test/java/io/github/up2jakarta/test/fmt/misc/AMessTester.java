package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IMessRecord;
import io.github.up2jakarta.test.fmt.tree.Tree90;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;

import static io.github.up2jakarta.csv.core.ModeType.MESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class AMessTester<R extends IMessRecord<SegmentType>> extends ANeatTester<R> {

    private final String key;

    public AMessTester(Invoice invoice, R[] output) {
        super(MESS, output);
        this.key = invoice.getReference();
    }

    public AMessTester(Tree90 root, R[] output) {
        super(MESS, output);
        this.key = root.getKey();
    }

    @Override
    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getOffset()]);
        assertEquals(key, row.getPivot());
    }

}
