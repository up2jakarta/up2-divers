package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Errors;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.ops.impl.GroupType;
import io.github.up2jakarta.csv.ops.impl.SegmentType;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice1;
import io.github.up2jakarta.csv.ops.impl.dto.Item;
import io.github.up2jakarta.csv.test.ABusinessTest;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.core.ops.ModeType.FAST;
import static io.github.up2jakarta.csv.ops.impl.SegmentType.*;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.FATAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
@SuppressWarnings("unchecked")
class SimpleInvoice1Tests extends ABusinessTest<Invoice1, SimpleRecord<GroupType, SegmentType>, SimpleError<GroupType, SegmentType>> {

    private final SimpleAggregator<Invoice1, GroupType, SegmentType> aggregator;
    private final MapperFactory<GroupType> factory;

    @Autowired
    SimpleInvoice1Tests(MapperFactory<GroupType> factory) throws BeanException {
        super(new SimpleAggregator<>(factory, Invoice1.class, S01));
        this.factory = factory;
        this.aggregator = (SimpleAggregator<Invoice1, GroupType, SegmentType>) super.aggregator;
    }

    @Test
    void testEmpty() throws BeanException {
        testEmpty(new SimpleRecord[0]);
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
        final SimpleAggregator<Invoice1, GroupType, SegmentType> aggregator = new SimpleAggregator<>(factory, Invoice1.class, S01, SegmentType.values());
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
        final SimpleAggregator<Invoice1, GroupType, SegmentType> aggregator = new SimpleAggregator<>(factory, Invoice1.class, S01, ERROR);
        final List<String[]> rows = Arrays.asList(
                new String[]{"01", "TU2025R0099", "2025-03-12", "120", "100", "20"},
                new String[]{"02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"}
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
                aggregator.record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                aggregator.record("01", "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        fastCardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                aggregator.record("01", "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        testCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                aggregator.record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                aggregator.record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                aggregator.record("02", "TU2025R0099", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                aggregator.record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        testCardinality3(S02, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                aggregator.record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                aggregator.record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS")
        };
        // When & Then
        testCardinality4(S04, S01, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType> detached = new SimpleRecord<>(S90, "9999", "Warning", "Detached");
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                aggregator.record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                aggregator.record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                aggregator.record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                aggregator.record("04", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
                detached,
        };
        // When & Then
        testDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = Tests.fastInvoice(S01);
        // When & Then
        testValid1(rows);
    }

    @Test
    void testValid2() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                aggregator.record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                aggregator.record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                aggregator.record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                aggregator.record("04", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        testValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final SimpleRecord<GroupType, SegmentType> invalid = new SimpleRecord<>(S90, "1199", "Support", null);
        final SimpleRecord<GroupType, SegmentType>[] rows = new SimpleRecord[]{
                aggregator.record("01", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                aggregator.record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                aggregator.record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                aggregator.record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };

        // When & Then
        testValidation(invalid, rows);
    }

    @Test
    void testValid() throws BeanException {
        // Given
        final List<String[]> rows = Arrays.asList(
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
                new String[]{"05", "TU2025R0099", "ES", "S.A. of capital 100.000$"}
        );
        // When Parsing
        final SimpleResult<Invoice1, ?> result = aggregator.parse(rows);
        final Invoice1 invoice = result.getBean();
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
