package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.*;
import io.github.up2jakarta.csv.api.hdl.IMutualRecord;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.core.BusinessExporter.Spec;
import io.github.up2jakarta.csv.data.Fixed06Generator;
import io.github.up2jakarta.csv.data.FullImporter;
import io.github.up2jakarta.csv.data.FullRecord;
import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.csv.hdl.PropertyFailureCollector;
import io.github.up2jakarta.lov.CodeListAdapter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Item;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import static io.github.up2jakarta.csv.api.ILinker.N;
import static io.github.up2jakarta.csv.core.ModeType.*;
import static io.github.up2jakarta.csv.data.Fixed06Generator.FV_SM;
import static io.github.up2jakarta.csv.data.MatrixPrinter.CONSOLE;
import static io.github.up2jakarta.csv.data.MatrixPrinter.MARKDOWN;
import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static io.github.up2jakarta.lov.core.Codes.encodeInt;
import static io.github.up2jakarta.lov.core.Codes.fixed;
import static io.github.up2jakarta.test.fmt.misc.ABusinessTest.assertValid;
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.copyOfRange;
import static org.junit.jupiter.api.Assertions.*;

public final class Tests {

    public static final String ERROR_CODE = "TU-V001";
    public static final CodeListAdapter<SegmentType> PARSER = new CodeListAdapter<>(SegmentType.class, "TU");

    public static final String[][] MESS_INVOICE = {
            new String[]{"01", "TU2025R0099", "2025-03-12", "120", "100", "20"},
            new String[]{"02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"},
            new String[]{"03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"},
            new String[]{"04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"},
            new String[]{"09", "TU2025R0099", "1199", "Support", "Yes"},
            new String[]{"09", "TU2025R0099", "1199", "Duration", "one year"},
            new String[]{"04", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"},
            new String[]{"09", "TU2025R0099", "2299", "Type", "Net"},
            new String[]{"09", "TU2025R0099", "2299", "Generation", "5th"},
            new String[]{"06", "TU2025R0099", "88", "10.00", "Delivery fees"},
            new String[]{"06", "TU2025R0099", "99", "10.00", "Delivery discount"},
            new String[]{"05", "TU2025R0099", "DD", "Due date: 31/12/2025"},
            new String[]{"05", "TU2025R0099", "ES", "S.A. of capital 100.000$"},
            new String[]{"08", "TU2025R0099", "PEE0099", "FR", "Paris", "75020", "99 Rue Up2JE", "Up2JE"},
            new String[]{"07", "TU2025R0099", "PER0099", "FR", "Paris", "75020", "99 Rue Up2JR", "Up2JR"},
    };

    public static final String[][] FULL_INVOICE;
    public static final String[][] NEAT_INVOICE;

    static {
        FULL_INVOICE = new String[MESS_INVOICE.length][];
        for (var i = 0; i < MESS_INVOICE.length; i++) {
            final String[] source = MESS_INVOICE[i];
            final String[] target = FULL_INVOICE[i] = new String[source.length + 1];
            System.arraycopy(source, 0, target, 1, source.length);
            target[0] = fixed(FV_SM + i);
        }

        NEAT_INVOICE = new String[MESS_INVOICE.length][];
        NEAT_INVOICE[0] = MESS_INVOICE[0];
        for (var i = 1; i < MESS_INVOICE.length; i++) {
            final String[] source = MESS_INVOICE[i];
            final int sp = "09".equals(source[0]) ? 3 : 2;
            final String[] target = NEAT_INVOICE[i] = new String[source.length - sp + 1];
            System.arraycopy(source, sp, target, 1, source.length - sp);
            target[0] = source[0];
        }
    }

    private Tests() {
    }

    private static SegmentType type(String code, SegmentType target) {
        if (target == S01 || !code.endsWith("1")) {
            return PARSER.parse(code);
        }
        final char[] result = new char[]{target.getCode().charAt(0), code.charAt(1)};
        return PARSER.parse(new String(result));
    }

    private static String[] columns(IMode mode, SegmentType type, final String[] data) {
        final int from = offset(mode, type);
        return copyOfRange(data, from, data.length);
    }

    private static <T extends Invoice, R extends IRecord<SegmentType>> T aggregate(IMode mode, BusinessImporter<TermType, SegmentType, T, R, ?> importer, R[] rows) {
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

    private static void tx(List<Spec<SegmentType, TermType>> specs, IMode mode) throws IOException {
        try (final PrintStream out = new PrintStream("./src/test/resources/" + mode + "_SPECS.txt", UTF_8)) {
            out.printf("Specification of Invoice %s Format", mode);
            out.println();
            specs.forEach(s -> {
                out.println();
                out.printf(s.term().toString());
                out.println();
                out.printf("- Segment: %s", s.key());
                out.println();
                out.printf("- Cardinality: %d..%s", s.min(), (s.max() == N) ? "n" : String.valueOf(s.max()));
                out.println();
                out.printf("- Class: %s", s.type().getName());
                out.println();
                CONSOLE.print(out, s.get());
            });
        }
    }

    private static void md(List<Spec<SegmentType, TermType>> specs, IMode mode) throws IOException {
        try (final PrintStream out = new PrintStream("./src/test/resources/" + mode + "_SPECS.md", UTF_8)) {
            out.printf("# Specification of Invoice `%s` Format", mode);
            out.println();
            out.println();
            specs.forEach(s -> {
                final int tab = (s.depth() - 1) * 4;
                final TermType t = s.term();
                out.printf("%s- `%s` [%s](#%s)", " ".repeat(tab), t.getCode(), t.getName(), t.name().toLowerCase());
                out.println();
            });
            out.println();
            specs.forEach(s -> {
                final TermType t = s.term();
                out.printf("<h2 id=\"%s\">`%s` %s </h2>", t.name().toLowerCase(), t.getCode(), t.getName());
                out.println();
                out.println();
                out.printf("- <b>Segment:</b> `%s` %s", s.key().getCode(), s.key().getName());
                out.println();
                out.printf("- <b>Cardinality:</b> `%d..%s`", s.min(), (s.max() == N) ? "n" : String.valueOf(s.max()));
                out.println();
                final String cn = s.type().getName();
                out.printf("- <b>Class:</b> [%s](../java/%s.java)", cn, cn.replace('.', '/'));
                out.println();
                out.println();
                MARKDOWN.print(out, s.get());
                out.println();
            });
        }
    }

    public static void assertReference(IMode mode, Invoice invoice) {
        assertNotNull(invoice.getRecord());
        if (mode == ModeType.NEAT) {
            if (invoice instanceof Dummy4Invoice || invoice instanceof Dummy5Invoice) {
                assertNull(invoice.getReference());
                assertFalse(invoice.getRecord() instanceof IMessRecord<?>);
            } else if (invoice instanceof Dummy1Invoice) {
                // Manual setting : BusinessObject#setReference(String)
                assertNotNull(invoice.getReference());
                assertInstanceOf(IMessRecord.class, invoice.getRecord());
                assertEquals(((IMessRecord<?>) invoice.getRecord()).getPivot(), invoice.getReference());
            } else {
                assertNotNull(invoice.getReference());
                assertInstanceOf(IMessRecord.class, invoice.getRecord());
                assertNull(((IMessRecord<?>) invoice.getRecord()).getPivot());
            }
        } else {
            assertNotNull(invoice.getReference());
            assertInstanceOf(IMessRecord.class, invoice.getRecord());
            assertEquals(((IMessRecord<?>) invoice.getRecord()).getPivot(), invoice.getReference());
        }
    }

    public static int offset(IMode mode, SegmentType type) {
        if (type == S01 || type == S11 || type == S41 || type == S51) {
            // keep the invoice key for roots When @Truncated is absent
            return mode.getOffset();
        }
        if (type == S90) {
            // keep the node key for roots
            return mode.getOffset();
        }
        return mode.getLength();
    }

    public static InputRecord record(SegmentType type, String... data) {
        final String reference = encodeInt(Arrays.hashCode(data));
        return new InputRecord(reference, type, "TU2025R0099", data);
    }

    @SuppressWarnings("unchecked")
    public static <R extends NeatRecord<SegmentType>> R[] neatInvoice(SegmentType target, BiFunction<SegmentType, String[], R> creator) {
        final Class<R> classType = (Class<R>) creator.apply(SegmentType.S00, new String[0]).getClass();
        final R[] result = (R[]) Array.newInstance(classType, NEAT_INVOICE.length);
        for (var i = 0; i < NEAT_INVOICE.length; i++) {
            final String[] data = NEAT_INVOICE[i];
            final SegmentType type = type(data[0], target);
            result[i] = creator.apply(type, columns(NEAT, type, data));
        }
        return result;
    }

    public static TURecord[] messInvoice(SegmentType target) {
        final TURecord[] result = new TURecord[MESS_INVOICE.length];
        for (var i = 0; i < MESS_INVOICE.length; i++) {
            final String[] data = MESS_INVOICE[i];
            final SegmentType type = type(data[0], target);
            result[i] = new TURecord(type, data[1], columns(MESS, type, data));
        }
        return result;
    }

    public static InputRecord[] invoice(SegmentType target, IMode mode) {
        final String[][] source = switch (mode) {
            case NEAT -> NEAT_INVOICE;
            case MESS -> MESS_INVOICE;
            default -> FULL_INVOICE;
        };
        final InputRecord[] result = new InputRecord[source.length];
        for (var i = 0; i < source.length; i++) {
            final String[] data = source[i];
            final SegmentType type = type(data[mode.getIndex()], target);
            final String rid = (mode == FULL) ? data[0] : null;
            final String bid = (mode != NEAT) ? data[mode.getIndex() + 1] : null;
            final InputRecord entity = new InputRecord(rid, type, bid, columns(mode, type, data));
            result[i] = entity;
        }
        return result;
    }

    public static <T extends Invoice, R extends IMessRecord<SegmentType>> void assertInvoice(
            MessImporter<TermType, SegmentType, T, R, ?> importer, R[] rows
    ) throws BeanException {
        // When Parsing
        final T invoice = aggregate(MESS, importer, rows);
        // When Formating
        final AMessTester<R> tc = new AMessTester<>(invoice, rows);
        importer.toExporter().format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

    public static <T extends Invoice, R extends IFullRecord<SegmentType>> void assertInvoice(
            FullImporter<TermType, SegmentType, T, R, ?> importer, R[] rows
    ) throws BeanException {
        // When Parsing
        final T invoice = aggregate(FULL, importer, rows);
        // When Formating
        final AFullTester<R> tc = new AFullTester<>(invoice, rows);
        final Fixed06Generator uid = new Fixed06Generator();
        importer.toExporter().format(invoice, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

    public static <T extends Invoice, R extends IRecord<SegmentType>, E extends IEvent<TermType>> void assertInvoice(
            NeatImporter<TermType, SegmentType, T, R, E> importer, R[] rows
    ) throws BeanException {
        // When Parsing
        final T invoice = aggregate(NEAT, importer, rows);
        // When Formating
        final ANeatTester<R> tc = new ANeatTester<>(rows);
        importer.toExporter().format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

    public static void specs(BusinessExporter<TermType, SegmentType, Invoice> exporter, IMode mode) throws IOException {
        final List<Spec<SegmentType, TermType>> specs = exporter.specs("", "Code", "Name", "Default");
        tx(specs, mode);
        md(specs, mode);
    }

    public static class TUCollector extends PropertyFailureCollector<TermType, TURecord, TUError> {
        public TUCollector(TURecord row) {
            super(row, TUError::new, FATAL);
        }
    }

    public static class TURecord extends FullRecord<SegmentType> implements IMutualRecord<TUError, TURecord> {

        private final List<TUError> errors = new LinkedList<>();

        // NEAT Mode compatibility
        public TURecord(SegmentType type, String[] data) {
            super(null, type, null, data);
        }

        // MESS Mode compatibility
        public TURecord(SegmentType type, String businessKey, String... data) {
            super(null, type, businessKey, data);
        }

        @Override
        public List<TUError> getEvents() {
            return errors;
        }
    }

    public static class TUError extends PropertyEvent<TermType, TURecord> implements IMutual<TURecord, TUError> {

        public TUError(TURecord row, Integer offset, TermType type, TypeException cause) {
            super(row, offset, type, cause);
        }
    }
}
