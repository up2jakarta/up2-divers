package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.IMode;
import io.github.up2jakarta.test.impl.SegmentType;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static io.github.up2jakarta.csv.core.ModeType.NEAT;
import static io.github.up2jakarta.test.fmt.misc.Tests.offset;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ANeatTester<R extends IRecord<SegmentType>> {

    final IMode mode;
    private final List<R> output;

    public ANeatTester(R[] output) {
        this(NEAT, output);
    }

    ANeatTester(IMode mode, R[] output) {
        this.mode = mode;
        this.output = new LinkedList<>(asList(output));
    }

    public final void assertExists(final String[] data) {
        assertTrue(data.length > mode.getIndex());
        for (var it = output.listIterator(); it.hasNext(); ) {
            final R row = it.next();
            final String type = row.getType().getCode();
            final String[] source = row.getData();
            final int offset = offset(mode, row.getType());
            if (type.equals(data[mode.getIndex()]) && data.length == (source.length + offset)) {
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
        // Default, not necessary extra assertions
    }

    public final void assertEmpty() {
        assertEquals(0, output.size());
    }

}
