package io.github.up2jakarta.csv.ops.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.ops.ModeType;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static io.github.up2jakarta.csv.ops.ModeType.FAST;
import static io.github.up2jakarta.csv.ops.misc.Tests.offset;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AFastTester<R extends IRecord<SegmentType>> {

    private final String key;
    private final ModeType mode;
    private final List<R> output;

    public AFastTester(BusinessObject invoice, R[] output) {
        this(FAST, invoice, output);
    }

    protected AFastTester(ModeType mode, BusinessObject invoice, R[] output) {
        this.mode = mode;
        this.key = invoice.getReference();
        this.output = new LinkedList<>(asList(output));
    }

    public final void assertExists(final String[] data) {
        assertTrue(data.length > 2);
        for (var it = output.listIterator(); it.hasNext(); ) {
            final R row = it.next();
            final String type = row.getType().getCode();
            final String[] source = row.getColumns();
            final int offset = offset(mode, row.getType()) + (("11".equals(type)) ? 1 : 0);
            if (type.equals(data[mode.getTypeIdIndex()]) && data.length == (source.length + offset)) {
                for (var i = 0; i < source.length; i++) {
                    if (!Objects.equals(source[i], data[i + offset])) {
                        break;
                    }
                }
                this.assertFound(row, data);
                it.remove();
                return;
            }
        }
        throw new AssertionFailedError(Arrays.toString(data) + " does not exists");
    }

    protected void assertFound(final R row, final String[] data) {
        assertEquals(key, data[mode.getBeanIdIndex()]);
    }

    public final void assertEmpty() {
        assertEquals(0, output.size());
    }
}
