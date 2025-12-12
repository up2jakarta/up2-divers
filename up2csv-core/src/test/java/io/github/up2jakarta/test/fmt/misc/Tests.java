package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IMutualRecord;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.core.hdl.PropertyFailureCollector;
import io.github.up2jakarta.csv.data.IMutual;
import io.github.up2jakarta.csv.fmt.Fixed06Generator;
import io.github.up2jakarta.csv.fmt.FullRecord;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.lov.CodeListAdapter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Item;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import static io.github.up2jakarta.csv.core.ModeType.*;
import static io.github.up2jakarta.csv.fmt.Fixed06Generator.FV_SM;
import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static io.github.up2jakarta.lov.core.Codes.encodeInt;
import static io.github.up2jakarta.lov.core.Codes.fixed;
import static io.github.up2jakarta.test.fmt.misc.ABusinessTest.assertValid;
import static io.github.up2jakarta.test.impl.GroupType.D001;
import static io.github.up2jakarta.test.impl.SegmentType.S01;
import static io.github.up2jakarta.test.impl.SegmentType.S09;
import static java.util.Arrays.copyOfRange;
import static org.junit.jupiter.api.Assertions.*;

public final class Tests {

    public static final String ERROR_CODE = "TU-V001";
    public static final CodeListAdapter<SegmentType> PARSER = new CodeListAdapter<>(SegmentType.class, "TU");

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
    public static final String[][] UNIT_INVOICE;

    static {
        FULL_INVOICE = new String[FAST_INVOICE.length][];
        for (var i = 0; i < FAST_INVOICE.length; i++) {
            final String[] source = FAST_INVOICE[i];
            final String[] target = FULL_INVOICE[i] = new String[source.length + 1];
            System.arraycopy(source, 0, target, 1, source.length);
            target[0] = fixed(FV_SM + i);
        }

        UNIT_INVOICE = new String[FAST_INVOICE.length][];
        UNIT_INVOICE[0] = FAST_INVOICE[0];
        for (var i = 1; i < FAST_INVOICE.length; i++) {
            final String[] source = FAST_INVOICE[i];
            final String[] target = UNIT_INVOICE[i] = new String[source.length - 1];
            System.arraycopy(source, 2, target, 1, source.length - 2);
            target[0] = source[0];
        }
    }

    private Tests() {
    }

    private static SegmentType type(String code, SegmentType target) {
        if ("90".equals(code)) {
            return S09;
        }
        if (target == S01) {
            return PARSER.parse(code);
        }
        final char[] result = new char[]{target.getCode().charAt(0), code.charAt(1)};
        return PARSER.parse(new String(result));
    }

    private static String[] columns(ModeType mode, SegmentType type, final String[] data) {
        final int from = offset(mode, type);
        return copyOfRange(data, from, data.length);
    }

    private static <T extends Invoice, R extends IRecord<SegmentType>> T aggregate(ModeType mode, BusinessImporter<GroupType, SegmentType, T, R, ?> importer, R[] rows) {
        // When Parsing
        final T invoice = importer.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(mode, invoice, 2, 2);
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

    public static void assertReference(ModeType mode, Invoice invoice) {
        assertNotNull(invoice.getRecord());
        if (mode == ModeType.UNIT) {
            if (invoice instanceof Dummy4Invoice || invoice instanceof Dummy5Invoice) {
                assertNull(invoice.getReference());
                assertFalse(invoice.getRecord() instanceof IFastRecord<?, ?>);
            } else if (invoice instanceof Dummy1Invoice) {
                // Manual setting : BusinessObject#setReference(String)
                assertNotNull(invoice.getReference());
                assertInstanceOf(IFastRecord.class, invoice.getRecord());
                assertEquals(((IFastRecord<?, ?>) invoice.getRecord()).getPivot(), invoice.getReference());
            } else {
                assertNotNull(invoice.getReference());
                assertInstanceOf(IFastRecord.class, invoice.getRecord());
                assertNull(((IFastRecord<?, ?>) invoice.getRecord()).getPivot());
            }
        } else {
            assertNotNull(invoice.getReference());
            assertInstanceOf(IFastRecord.class, invoice.getRecord());
            assertEquals(((IFastRecord<?, ?>) invoice.getRecord()).getPivot(), invoice.getReference());
        }
    }

    public static int offset(ModeType mode, SegmentType type) {
        if (type.getDataType() == D001 && !type.getClassType().isAnnotationPresent(Truncated.class)) {
            // keep the invoice key for roots
            return mode.getBeanIdIndex();
        }
        return mode.getLength();
    }

    public static InputRecord record(SegmentType type, String... data) {
        final String reference = encodeInt(Arrays.hashCode(data));
        return new InputRecord(reference, type, "TU2025R0099", data);
    }

    @SuppressWarnings("unchecked")
    public static <R extends UnitRecord<SegmentType>> R[] unitInvoice(SegmentType target, BiFunction<SegmentType, String[], R> creator) {
        final Class<R> classType = (Class<R>) creator.apply(SegmentType.S00, new String[0]).getClass();
        final R[] result = (R[]) Array.newInstance(classType, UNIT_INVOICE.length);
        for (var i = 0; i < UNIT_INVOICE.length; i++) {
            final String[] data = UNIT_INVOICE[i];
            final SegmentType type = type(data[UNIT.getTypeIdIndex()], target);
            result[i] = creator.apply(type, columns(UNIT, type, data));
        }
        return result;
    }

    public static TURecord[] fastInvoice(SegmentType target) {
        final TURecord[] result = new TURecord[FAST_INVOICE.length];
        for (var i = 0; i < FAST_INVOICE.length; i++) {
            final String[] data = FAST_INVOICE[i];
            final SegmentType type = type(data[0], target);
            result[i] = new TURecord(type, data[FAST.getBeanIdIndex()], columns(FAST, type, data));
        }
        return result;
    }

    public static InputRecord[] invoice(SegmentType target, ModeType mode) {
        final String[][] source = switch (mode) {
            case UNIT -> UNIT_INVOICE;
            case FAST -> FAST_INVOICE;
            default -> FULL_INVOICE;
        };
        final InputRecord[] result = new InputRecord[source.length];
        for (var i = 0; i < source.length; i++) {
            final String[] data = source[i];
            final SegmentType type = type(data[mode.getTypeIdIndex()], target);
            final String rid = (mode == FULL) ? data[0] : null;
            final String bid = (mode != UNIT) ? data[mode.getBeanIdIndex()] : null;
            final InputRecord entity = new InputRecord(rid, type, bid, columns(mode, type, data));
            result[i] = entity;
        }
        return result;
    }

    public static <T extends Invoice, R extends IFastRecord<SegmentType, ?>> void assertInvoice(
            FastImporter<GroupType, SegmentType, T, R, ?> importer, R[] rows
    ) throws BeanException, IOException {
        // When Parsing
        final T invoice = aggregate(FAST, importer, rows);
        // When Formating
        final AFastTester<R> tc = new AFastTester<>(invoice, rows);
        importer.toExporter().format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

    public static <T extends Invoice, R extends IFullRecord<SegmentType, ?>> void assertInvoice(
            FullImporter<GroupType, SegmentType, T, R, ?> importer, R[] rows
    ) throws BeanException, IOException {
        // When Parsing
        final T invoice = aggregate(FULL, importer, rows);
        // When Formating
        final AFullTester<R> tc = new AFullTester<>(invoice, rows);
        importer.toExporter().format(invoice, new Fixed06Generator(), tc::assertExists);
        tc.assertEmpty();
    }

    public static <T extends Invoice, R extends IRecord<SegmentType>, E extends IEvent<GroupType>> void assertInvoice(
            UnitImporter<GroupType, SegmentType, T, R, E> importer, R[] rows
    ) throws BeanException, IOException {
        // When Parsing
        final T invoice = aggregate(UNIT, importer, rows);
        // When Formating
        final AUnitTester<R> tc = new AUnitTester<>(rows);
        importer.toExporter().format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

    public static class TUCollector extends PropertyFailureCollector<GroupType, TURecord, TUError> {
        public TUCollector(TURecord row) {
            super(row, TUError::new, FATAL);
        }
    }

    public static class TURecord extends FullRecord<SegmentType, String> implements IMutualRecord<TUError, TURecord> {

        private final List<TUError> errors = new LinkedList<>();

        // UNIT Mode compatibility
        public TURecord(SegmentType type, String[] data) {
            super(null, type, null, data);
        }

        // FAST Mode compatibility
        public TURecord(SegmentType type, String businessKey, String... data) {
            super(null, type, businessKey, data);
        }

        @Override
        public List<TUError> getEvents() {
            return errors;
        }
    }

    public static class TUError extends PropertyEvent<GroupType, TURecord> implements IMutual<TURecord, TUError> {

        public TUError(TURecord row, Integer offset, GroupType type, TypeException cause) {
            super(row, offset, type, cause);
        }
    }
}
