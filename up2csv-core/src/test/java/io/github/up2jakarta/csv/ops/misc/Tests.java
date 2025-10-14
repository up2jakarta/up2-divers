package io.github.up2jakarta.csv.ops.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputRowEntity;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.SimpleRecord;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.impl.dto.Item;
import io.github.up2jakarta.csv.ops.*;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.csv.ops.ModeType.FAST;
import static io.github.up2jakarta.csv.ops.ModeType.FULL;
import static io.github.up2jakarta.csv.ops.misc.ABusinessTest.assertValid;
import static io.github.up2jakarta.xml.adapters.KeyCoder.*;
import static java.util.Arrays.copyOfRange;
import static org.junit.jupiter.api.Assertions.*;

public final class Tests {

    public static final String ERROR_CODE = "TU-V001";

    public static final String[][] FAST_INVOICE = {
            new String[]{"01", "TU2025R0099", "2025-03-12", "120", "100", "20"},
            new String[]{"02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"},
            new String[]{"03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"},
            new String[]{"04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"},
            new String[]{"04", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"},
            new String[]{"90", "TU2025R0099", "1199", "Support", "Yes"},
            new String[]{"90", "TU2025R0099", "1199", "Duration", "one year"},
            new String[]{"90", "TU2025R0099", "2299", "Type", "Net"},
            new String[]{"90", "TU2025R0099", "2299", "Generation", "5th"},
            new String[]{"06", "TU2025R0099", "90", "10.00", "Delivery fees"},
            new String[]{"06", "TU2025R0099", "99", "10.00", "Delivery discount"},
            new String[]{"05", "TU2025R0099", "DD", "Due date: 31/12/2025"},
            new String[]{"05", "TU2025R0099", "ES", "S.A. of capital 100.000$"},
            new String[]{"08", "TU2025R0099", "PEE0099", "FR", "Paris", "75020", "99 Rue Up2JE", "Up2JE"},
            new String[]{"07", "TU2025R0099", "PER0099", "FR", "Paris", "75020", "99 Rue Up2JR", "Up2JR"},
    };

    public static final String[][] FULL_INVOICE;

    static {
        FULL_INVOICE = new String[FAST_INVOICE.length][];
        for (var i = 0; i < FAST_INVOICE.length; i++) {
            final String[] source = FAST_INVOICE[i];
            final String[] target = FULL_INVOICE[i] = new String[source.length + 1];
            System.arraycopy(source, 0, target, 1, source.length);
            target[0] = fixed(i + 1);
        }
    }

    private Tests() {
    }

    private static SegmentType type(String code, SegmentType target) {
        if ("90".equals(code)) {
            return S90;
        }
        if (target == S01) {
            return valueOf('S' + code);
        }
        final char[] result = new char[]{'S', target.getCode().charAt(0), code.charAt(1)};
        return valueOf(new String(result));
    }

    private static String[] columns(ModeType mode, SegmentType type, final String[] data) {
        final int from = offset(mode, type);
        return copyOfRange(data, from, data.length);
    }

    public static int offset(ModeType mode, SegmentType type) {
        if (type == S01 || type == S11) {
            // keep the invoice key
            return mode.getBeanIdIndex();
        }
        return mode.getBeanIdIndex() + 1;
    }

    public static InputRowEntity record(SegmentType type, String... columns) {
        final InputRowEntity entity = new InputRowEntity();
        entity.setBusinessReference("TU2025R0099");
        entity.setReference(encodeInt(Arrays.hashCode(columns)));
        entity.setColumns(columns);
        entity.setType(type);
        return entity;
    }

    private static <T extends Invoice, R extends IRecord<SegmentType>> T assertAggregate(
            BusinessAggregator<T, GroupType, SegmentType, R, ?> aggregator, R[] rows
    ) throws BeanException {
        // When Parsing
        final T invoice = aggregator.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(invoice, 2, 2);
        assertNotNull(invoice.getSeller());
        assertNotNull(invoice.getBuyer());
        assertNotNull(invoice.getPayer());
        assertNotNull(invoice.getPayee());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 2);
        }
        return invoice;
    }

    public static SimpleRecord<GroupType, SegmentType>[] fastInvoice(SegmentType target) {
        //noinspection unchecked
        final SimpleRecord<GroupType, SegmentType>[] result = new SimpleRecord[FAST_INVOICE.length];
        for (var i = 0; i < FAST_INVOICE.length; i++) {
            final String[] data = FAST_INVOICE[i];
            final SegmentType type = type(data[0], target);
            result[i] = new SimpleRecord<>(type, data[FAST.getBeanIdIndex()], columns(FAST, type, data));
        }
        return result;
    }

    public static InputRowEntity[] fullInvoice(SegmentType target) {
        final InputRowEntity[] result = new InputRowEntity[FULL_INVOICE.length];
        for (var i = 0; i < FULL_INVOICE.length; i++) {
            final String[] data = FULL_INVOICE[i];
            final SegmentType type = type(data[1], target);
            final InputRowEntity entity = record(type, columns(FULL, type, data));
            entity.setReference(data[0]);
            {
                final InputRowEntity.PKey key = new InputRowEntity.PKey();
                entity.setKey(key);
                key.setRecordNumber(decode(data[0]));
            }
            entity.setBusinessReference(data[2]);
            result[i] = entity;
        }
        return result;
    }

    public static void assertExists(final String[] data, final List<String[]> rows) {
        assertTrue(data.length > 2);
        for (var it = rows.listIterator(); it.hasNext(); ) {
            final String[] source = it.next();
            if (source.length == data.length) {
                for (var i = 0; i < source.length; i++) {
                    if (!source[i].equals(data[i])) {
                        break;
                    }
                }
                it.remove();
                return;
            }
        }
        throw new AssertionFailedError(Arrays.toString(data) + " does not exists");
    }

    public static <T extends Invoice, R extends IRecord<SegmentType>> void assertInvoice(
            FastAggregator<T, GroupType, SegmentType, R, ?> aggregator, R[] rows
    ) throws BeanException, IOException {
        // When Parsing
        final T invoice = assertAggregate(aggregator, rows);
        // When Formating
        final AFastTester<R> tc = new AFastTester<>(invoice, rows);
        aggregator.format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

    public static <T extends Invoice, R extends IRecordEntity<SegmentType, ?, ?>> void assertInvoice(
            FullAggregator<T, GroupType, SegmentType, R, ?> aggregator, R[] rows
    ) throws BeanException, IOException {
        // When Parsing
        final T invoice = assertAggregate(aggregator, rows);
        // When Formating
        final AFullTester<R> tc = new AFullTester<>(invoice, rows);
        aggregator.format(invoice, new Fixed08Generator(1), tc::assertExists);
        tc.assertEmpty();
    }

}
