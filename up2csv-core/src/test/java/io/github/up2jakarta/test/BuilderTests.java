package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.api.hdl.IMutualRecord;
import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.BusinessEvent;
import io.github.up2jakarta.csv.data.IMutual;
import io.github.up2jakarta.csv.fmt.*;
import io.github.up2jakarta.lov.IError;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.fmt.misc.Dummy4Invoice;
import io.github.up2jakarta.test.fmt.misc.Tests;
import io.github.up2jakarta.test.impl.InputError;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.impl.dto.Invoice;
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
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.test.fmt.misc.Tests.*;
import static io.github.up2jakarta.test.impl.SegmentType.S01;
import static io.github.up2jakarta.test.impl.SegmentType.S41;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    private final Up2Factory<TermType> factory;

    @Autowired
    BuilderTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    @Test
    void testSimpleUnitExporter() throws BeanException, IOException {
        // WHEN
        final SimpleUnitExporter<Invoice, TermType, SegmentType> exporter = factory.builder()
                .unit(Invoice.class)
                .build(SegmentType.class)
                .export();
        assertNotNull(exporter);
        // THEN
        final String[] root = UNIT_INVOICE[0];
        exporter.format(INVOICE, d -> assertArrayEquals(root, d));
        // SPECS
        Tests.specs(exporter, UNIT);
    }

    @Test
    void testSimpleUnitImporter() throws BeanException, IOException {
        // WHEN
        final SimpleUnitImporter<Dummy4Invoice, TermType, SegmentType> importer = factory.builder()
                .unit(Dummy4Invoice.class)
                .build(SegmentType.class)
                .build();
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S41, UnitRecord::new));
    }

    @Test
    void testUnit1Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .unit(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01, TURecord::new));
    }

    @Test
    void testUnit2Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<TermType, SegmentType, Invoice, InputRecord, InputError> importer = factory.builder()
                .unit(Invoice.class)
                .build(SegmentType.class)
                .build(InputError::new, (r) -> 0);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, invoice(S01, UNIT));
    }

    @Test
    void testUnit3Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .unit(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01, TURecord::new));
    }

    @Test
    void testUnit4Importer() throws BeanException, IOException {
        // WHEN
        final UnitImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .unit(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01, TURecord::new));
    }

    @Test
    void testUnit5Importer() throws BeanException, IOException {
        // GIVEN
        final BSRecord[] rows = Arrays.stream(invoice(S01, UNIT)).map(BSRecord::new).toArray(BSRecord[]::new);
        // WHEN
        final UnitImporter<TermType, SegmentType, Invoice, BSRecord, BSError> importer = factory.builder()
                .unit(Invoice.class)
                .build(SegmentType.class)
                .build(BSError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, rows);
    }

    @Test
    void testSimpleFastExporter() throws BeanException, IOException {
        // WHEN
        final SimpleFastExporter<Invoice, TermType, SegmentType> exporter = factory.builder()
                .fast(Invoice.class)
                .build(SegmentType.class)
                .export();
        assertNotNull(exporter);
        // THEN
        final String[] root = FAST_INVOICE[0];
        exporter.format(INVOICE, d -> assertArrayEquals(root, d));
        // SPECS
        Tests.specs(exporter, FAST);
    }

    @Test
    void testSimpleFastImporter() throws BeanException, IOException {
        // WHEN
        final SimpleFastImporter<Invoice, TermType, SegmentType> importer = factory.builder()
                .fast(Invoice.class)
                .build(SegmentType.class)
                .build();
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast1Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .fast(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast2Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .fast(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast3Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<TermType, SegmentType, Invoice, InputRecord, InputError> importer = factory.builder()
                .fast(Invoice.class)
                .build(SegmentType.class)
                .build(InputError::new, (r) -> 0);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, invoice(S01, FAST));
    }

    @Test
    void testFast4Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .fast(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast5Importer() throws BeanException, IOException {
        // GIVEN
        final BSRecord[] rows = Arrays.stream(invoice(S01, FAST)).map(BSRecord::new).toArray(BSRecord[]::new);
        // WHEN
        final FastImporter<TermType, SegmentType, Invoice, BSRecord, BSError> importer = factory.builder()
                .fast(Invoice.class)
                .build(SegmentType.class)
                .build(BSError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, rows);
    }

    @Test
    void testSimpleFullExporter() throws BeanException, IOException {
        // WHEN
        final SimpleFullExporter<Invoice, TermType, SegmentType> exporter = factory.builder()
                .full(Invoice.class)
                .build(SegmentType.class)
                .export();
        assertNotNull(exporter);
        // THEN
        final String[] root = FULL_INVOICE[0];
        exporter.format(INVOICE, new Fixed06Generator(), d -> assertArrayEquals(root, d));
        // SPECS
        Tests.specs(exporter, FULL);
    }

    @Test
    void testFull1Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<TermType, SegmentType, Invoice, InputRecord, InputError> importer = factory.builder()
                .full(Invoice.class)
                .build(SegmentType.class)
                .build(InputError::new, (r) -> 0);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, invoice(S01, FULL));
    }

    @Test
    void testFull2Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .full(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFull3Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .full(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFull4Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer = factory.builder()
                .full(Invoice.class)
                .build(SegmentType.class)
                .build(TUError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFull5Importer() throws BeanException, IOException {
        // GIVEN
        final BSRecord[] rows = Arrays.stream(invoice(S01, FULL)).map(BSRecord::new).toArray(BSRecord[]::new);
        // WHEN
        final FullImporter<TermType, SegmentType, Invoice, BSRecord, BSError> importer = factory.builder()
                .full(Invoice.class)
                .build(SegmentType.class)
                .build(BSError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, rows);
    }

    public static class BSRecord extends FullRecord<SegmentType> implements IMutualRecord<BSError, BSRecord> {

        private final List<BSError> errors = new LinkedList<>();

        public BSRecord(SegmentType type, String pivot, String... data) {
            super(null, type, pivot, data);
        }

        public BSRecord(InputRecord r) {
            super(r.getKey(), r.getType(), r.getPivot(), r.getData());
        }

        @Override
        public List<BSError> getEvents() {
            return errors;
        }
    }

    public static class BSError extends BusinessEvent<TermType, BSRecord> implements IMutual<BSRecord, BSError> {

        public BSError(BSRecord row, int order, TermType type, Integer offset, IError cause, String trace) {
            super(row, order, type, offset, cause, trace);
        }
    }
}
