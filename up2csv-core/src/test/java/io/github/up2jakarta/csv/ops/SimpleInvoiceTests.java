package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Errors;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.impl.dto.Item;
import io.github.up2jakarta.csv.ops.misc.AFastTest;
import io.github.up2jakarta.csv.ops.misc.Tests;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.csv.ops.ModeType.FAST;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.FATAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
@SuppressWarnings("unchecked")
class SimpleInvoiceTests extends AFastTest<Invoice, SimpleRecord<GroupType, SegmentType>, SimpleError<GroupType, SegmentType>> {

    private final SimpleAggregator<Invoice, GroupType, SegmentType> aggregator;
    private final MapperFactory<GroupType> factory;

    @Autowired
    SimpleInvoiceTests(MapperFactory<GroupType> factory) throws BeanException {
        super(factory.builder().fast(Invoice.class).full(S01).build());
        this.aggregator = this.get();
        this.factory = factory;
    }

    private SimpleRecord<GroupType, SegmentType> record(String... row) throws CodeListException {
        return aggregator.record(row);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new SimpleRecord[0]);
    }

    @Test
    void testCodeListException() {
        // Given
        final List<String[]> rows = Arrays.asList(
                new String[]{"RT", "TU2025R0099"},
                new String[]{"01", "TU2025R0099"}
        );
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> aggregator.parse(rows));
        // Then
        assertEquals(Errors.ERROR_CODE_LIST, error.getCode());
        assertEquals(ERROR, error.getSeverity());
        assertEquals("Unknown value [RT] for CodeList[SegmentType]", error.getMessage());
    }

    @Test
    void testFatalException1() throws BeanException {
        // Given
        final SimpleAggregator<Invoice, GroupType, SegmentType> aggregator = new SimpleAggregator<>(factory, Invoice.class, S01);
        final List<String[]> rows = Arrays.asList(
                new String[]{"01", "TU2025R0099", "2025-03-12", "120", "100", "20"},
                new String[]{"01", "TU2025R0099", "2025-03-12", "120", "100", "20"}
        );
        // When
        final FatalException error = assertThrows(FatalException.class, () -> aggregator.parse(rows));
        // Then
        assertEquals(GroupType.D001, error.getDataType());
        assertEquals(FATAL, error.getSeverity());
        assertEquals(FAST.getBeanIdIndex(), error.getOffset());
        assertEquals("io.github.up2jakarta.xml.clv.PropertyException: cardinality must be 1 and only one", error.getMessage());
    }

    @Test
    void testFatalException2() throws BeanException {
        // Given
        final SimpleAggregator<Invoice, GroupType, SegmentType> aggregator = new SimpleAggregator<>(factory, Invoice.class, S01, ERROR);
        final List<String[]> rows = Arrays.asList(
                new String[]{"01", "TU2025R0099", "2025-03-12", "120", "100", "20"},
                new String[]{"02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"},
                new String[]{"03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"}
        );
        // When
        final FatalException error = assertThrows(FatalException.class, () -> aggregator.parse(rows));
        // Then
        assertEquals(GroupType.D004, error.getDataType());
        assertEquals(ERROR, error.getSeverity());
        assertEquals(FAST.getBeanIdIndex(), error.getOffset());
        assertEquals("io.github.up2jakarta.xml.clv.PropertyException: cardinality must be greater than or equal to 1", error.getMessage());
    }

    @Test
    void testCardinality1() {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("01", "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check1Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                record("01", "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("02", "TU2025R0099", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S04, S01, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType> detached = new SimpleRecord<>(S90, "TU2025R0099", "9999", "Warning", "Detached");
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                record("04", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = Tests.fastInvoice(S01);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                record("04", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType> invalid = new SimpleRecord<>(S90, "TU2025R0099", "1199", "Support", null);
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };

        // When & Then
        checkValidation(invalid, rows);
    }

    @Test
    void testValid() throws BeanException, IOException {
        // Given
        final List<String[]> rows = Arrays.asList(Tests.FAST_INVOICE);
        // When Parsing
        final SimpleResult<Invoice, ?> result = aggregator.parse(rows);
        final Invoice invoice = result.getBean();
        // Then
        assertEquals(0, result.getErrors().size());
        assertValid(invoice, 2, 2);
        assertValid(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 2);
        }
        // When Formating
        final List<String[]> source = new LinkedList<>(rows);
        aggregator.format(invoice, d -> {
            assertEquals(invoice.getReference(), d[FAST.getBeanIdIndex()]);
            Tests.assertExists(d, source);
        });
        assertEquals(0, source.size());
    }

}
