package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.hdl.ISelfEvent;
import io.github.up2jakarta.csv.api.hdl.ISelfRecord;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.core.hdl.BusinessEvent;
import io.github.up2jakarta.csv.fmt.*;
import io.github.up2jakarta.csv.fmt.misc.CyclicInvoice;
import io.github.up2jakarta.csv.fmt.misc.Dummy4Invoice;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.xml.api.IError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.core.ModeType.*;
import static io.github.up2jakarta.csv.fmt.misc.Tests.*;
import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BuilderTests {

    private final Invoice INVOICE = new Invoice() {{
        setReference("TU2025R0099");
        setIssueDate(LocalDate.of(2025, 3, 12));
        setGrossAmount(new BigDecimal(120));
        setNetAmount(new BigDecimal(100));
        setTaxAmount(new BigDecimal(20));
    }};

    private final Up2Factory<GroupType> factory;

    @Autowired
    BuilderTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testSimpleUnitExporter() throws BeanException, IOException {
        // WHEN
        final SimpleUnitExporter<Invoice, GroupType, SegmentType> exporter = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .format();
        assertNotNull(exporter);
        // THEN
        final String[] root = UNIT_INVOICE[0];
        exporter.format(INVOICE, d -> assertArrayEquals(root, d));
    }

    @Test
    void testSimpleUnitImporter() throws BeanException, IOException {
        // WHEN
        final SimpleUnitImporter<Dummy4Invoice, GroupType, SegmentType> importer = factory.builder()
                .unit(Dummy4Invoice.class)
                .build(S41)
                .build();
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S41, UnitRecord::new));
    }

    @Test
    void testUnit1Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01, TURecord::new));
    }

    @Test
    void testUnit2Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> importer = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .build(InputError::new, (r) -> 0);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, invoice(S01, UNIT));
    }

    @Test
    void testUnit3Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .build(TUError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01, TURecord::new));
    }

    @Test
    void testUnit4Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .build(TUError::new, null);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01, TURecord::new));
    }

    @Test
    void testUnit5Importer() throws BeanException, IOException {
        // GIVEN
        final BSRecord[] rows = Arrays.stream(invoice(S01, UNIT)).map(BSRecord::new).toArray(BSRecord[]::new);
        // WHEN
        final UnitImporter<GroupType, SegmentType, Invoice, BSRecord, BSError> importer = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .build(BSError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, rows);
    }

    @Test
    void testSimpleFastExporter() throws BeanException, IOException {
        // WHEN
        final SimpleFastExporter<Invoice, GroupType, SegmentType> exporter = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .format();
        assertNotNull(exporter);
        // THEN
        final String[] root = FAST_INVOICE[0];
        exporter.format(INVOICE, d -> assertArrayEquals(root, d));
    }

    @Test
    void testSimpleFastImporter() throws BeanException, IOException {
        // WHEN
        final SimpleFastImporter<Invoice, GroupType, SegmentType> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build();
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast1Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast2Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build(TUError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast3Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build(InputError::new, (r) -> 0);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, invoice(S01, FAST));
    }

    @Test
    void testFast4Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build(TUError::new, null);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast5Importer() throws BeanException, IOException {
        // GIVEN
        final BSRecord[] rows = Arrays.stream(invoice(S01, FAST)).map(BSRecord::new).toArray(BSRecord[]::new);
        // WHEN
        final FastImporter<GroupType, SegmentType, Invoice, BSRecord, BSError> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build(BSError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, rows);
    }

    @Test
    void testSimpleFullExporter() throws BeanException, IOException {
        // WHEN
        final SimpleFullExporter<Invoice, GroupType, SegmentType> exporter = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .format();
        assertNotNull(exporter);
        // THEN
        final String[] root = FULL_INVOICE[0];
        exporter.format(INVOICE, new Fixed06Generator(), d -> assertArrayEquals(root, d));
    }

    @Test
    void testFull1Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> importer = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .build(InputError::new, (r) -> 0);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, invoice(S01, FULL));
    }

    @Test
    void testFull2Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .build(TUError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFull3Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFull4Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .build(TUError::new, null);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFull5Importer() throws BeanException, IOException {
        // GIVEN
        final BSRecord[] rows = Arrays.stream(invoice(S01, FULL)).map(BSRecord::new).toArray(BSRecord[]::new);
        // WHEN
        final FullImporter<GroupType, SegmentType, Invoice, BSRecord, BSError> importer = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .build(BSError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, rows);
    }

    @Test
    void testTypingException() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(Invoice.class)
                        .build(S11)
                        .build((r) -> 0)
        );
        assertNotNull(ex);
        // THEN
        assertEquals("Invoice[class] - Invalid business typing", ex.getMessage());
    }

    @Test
    void testRecursiveException() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(CyclicInvoice.class)
                        .build(S61)
                        .build()
        );
        assertNotNull(ex);
        // THEN
        assertEquals("SegmentType[61] - cyclic segment is not allowed: CyclicInvoice > CyclicInvoice.CyclicItem > CyclicInvoice", ex.getMessage());
    }

    public static class BSRecord extends FullRecord<SegmentType, String> implements ISelfRecord<GroupType, SegmentType, BSError, BSRecord> {

        private final List<BSError> errors = new LinkedList<>();

        public BSRecord(InputRecord r) {
            super(r.getKey(), r.getType(), r.getPivot(), r.getColumns());
        }

        @Override
        public List<BSError> getErrors() {
            return errors;
        }
    }

    public static class BSError extends BusinessEvent<GroupType, BSRecord> implements ISelfEvent<GroupType, BSRecord, BSError> {

        public BSError(BSRecord row, int order, GroupType type, Integer offset, IError cause, String trace) {
            super(row, order, type, offset, cause, trace);
        }
    }
}
