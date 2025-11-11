package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.impl.SegmentType;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static io.github.up2jakarta.csv.core.ModeType.UNIT;
import static io.github.up2jakarta.csv.fmt.misc.Tests.offset;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AUnitTester<R extends IRecord<SegmentType>> {

    final ModeType mode;
    private final List<R> output;

    public AUnitTester(R[] output) {
        this(UNIT, output);
    }

    AUnitTester(ModeType mode, R[] output) {
        this.mode = mode;
        this.output = new LinkedList<>(asList(output));
    }

    public final void assertExists(final String[] data) {
        assertTrue(data.length > mode.getTypeIdIndex());
        for (var it = output.listIterator(); it.hasNext(); ) {
            final R row = it.next();
            final String type = row.getType().getCode();
            final String[] source = row.getColumns();
            final int offset = offset(mode, row.getType());
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
        throw new AssertionFailedError(Arrays.toString(data) + "\tdoes not exists");
    }

    protected void assertFound(final R row, final String[] data) {
    }

    public final void assertEmpty() {
        assertEquals(0, output.size());
    }

}
