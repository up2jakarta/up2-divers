package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.*;
import io.github.up2jakarta.csv.fmt.hdl.Fixed06Generator;
import io.github.up2jakarta.csv.fmt.misc.CyclicInvoice;
import io.github.up2jakarta.csv.fmt.misc.MyError;
import io.github.up2jakarta.csv.fmt.misc.MyRecord;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static io.github.up2jakarta.csv.core.ModeType.FULL;
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
    void testUnitExporter() throws BeanException, IOException {
        // WHEN
        final UnitExporter<Invoice, GroupType, SegmentType> exporter = factory.builder()
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
        final SimpleUnitImporter<Invoice, GroupType, SegmentType> importer = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .build();
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01));
    }

    @Test
    void testUnitImporter() throws BeanException, IOException {
        // WHEN
        final UnitImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> importer = factory.builder()
                .unit(Invoice.class)
                .build(S01)
                .build(MyError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, unitInvoice(S01));
    }

    @Test
    void testFastExporter() throws BeanException, IOException {
        // WHEN
        final FastExporter<Invoice, GroupType, SegmentType> exporter = factory.builder()
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
        final FastImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build(MyError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFast2Importer() throws BeanException, IOException {
        // WHEN
        final FastImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> importer = factory.builder()
                .fast(Invoice.class)
                .build(S01)
                .build(MyError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFullExporter() throws BeanException, IOException {
        // WHEN
        final FullExporter<Invoice, GroupType, SegmentType> exporter = factory.builder()
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
        final FullImporter<Invoice, GroupType, SegmentType, InputRecord, InputError> importer = factory.builder()
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
        final FullImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> importer = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .build(MyError::new, ERROR);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
    }

    @Test
    void testFull3Importer() throws BeanException, IOException {
        // WHEN
        final FullImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> importer = factory.builder()
                .full(Invoice.class)
                .build(S01)
                .build(MyError::new);
        assertNotNull(importer);
        // THEN
        assertInvoice(importer, fastInvoice(S01));
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
        assertEquals("SegmentType[61] - cyclic segment is not allowed: CyclicInvoice > CyclicItem > CyclicInvoice", ex.getMessage());
    }

}
