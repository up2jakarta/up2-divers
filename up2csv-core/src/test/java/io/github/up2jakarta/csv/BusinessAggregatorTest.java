package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.impl.InputErrorEntity;
import io.github.up2jakarta.csv.impl.InputRowEntity;
import io.github.up2jakarta.csv.impl.InvoiceAggregator;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.test.agg.Attribute;
import io.github.up2jakarta.csv.test.agg.Invoice;
import io.github.up2jakarta.csv.test.agg.Item;
import io.github.up2jakarta.csv.test.agg.Party;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.impl.DataId.*;
import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.csv.test.Tests.create;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class BusinessAggregatorTest {

    private final InvoiceAggregator aggregator;

    @Autowired
    BusinessAggregatorTest(InvoiceAggregator aggregator) {
        this.aggregator = aggregator;
    }

    private static void assertValid(Party party) {
        assertNotNull(party);
        assertNotNull(party.getName());
        assertNotNull(party.getAddress());
        assertNotNull(party.getAddress().getCountry());
        assertNotNull(party.getAddress().getZipCode());
        assertNotNull(party.getAddress().getCity());
        assertNotNull(party.getAddress().getAddressLine1());
        assertNotNull(party.getAddress().getAddressLine2());
    }

    private static void assertValid(long id, Attribute attribute) {
        assertNotNull(attribute);
        assertNotNull(attribute.getKey());
        assertNotNull(attribute.getValue());
        assertEquals(id, attribute.getId());
    }

    private static void assertValid(Item item, int size) {
        assertNotNull(item);
        assertNotNull(item.getId());
        assertNotNull(item.getProduct());
        assertNotNull(item.getQuantity());
        assertNotNull(item.getGrossAmount());
        assertNotNull(item.getNetAmount());
        assertNotNull(item.getTaxAmount());
        assertEquals(size, item.getAttributes().size());
        for (final Attribute attribute : item.getAttributes()) {
            assertValid(item.getId(), attribute);
        }
    }

    private static void assertValid(Invoice invoice) {
        assertNotNull(invoice);
        assertNotNull(invoice.getReference());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getGrossAmount());
        assertNotNull(invoice.getNetAmount());
        assertNotNull(invoice.getTaxAmount());
    }

    @Test
    void testNull() throws BeanException {
        // Given
        final InputRowEntity[] rows = null;
        final List<InputErrorEntity> errors = new LinkedList<>();
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNull(invoice);
        assertEquals(0, errors.size());
    }

    @Test
    void testEmpty() throws BeanException {
        // Given
        final InputRowEntity[] rows = {};
        final List<InputErrorEntity> errors = new LinkedList<>();
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNull(invoice);
        assertEquals(0, errors.size());
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S01, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        final List<InputErrorEntity> errors = new LinkedList<>();
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNull(invoice);
        assertEquals(2, errors.size());
        for (var e : errors) {
            assertNull(e.getTrace());
            assertEquals(D001, e.getType());
            assertEquals(0, e.getOffset());
            assertEquals(SeverityType.FATAL, e.getSeverity());
            assertEquals(S01.getErrorCode(), e.getCode());
            assertEquals("size must be between 1 and 1", e.getMessage());
        }
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        final List<InputErrorEntity> errors = new LinkedList<>();
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertEquals(2, errors.size());
        for (var e : errors) {
            assertNull(e.getTrace());
            assertEquals(0, e.getOffset());
            assertEquals(SeverityType.ERROR, e.getSeverity());
            assertNotNull(e.getCode());
            assertTrue(e.getType() == D002 || e.getType() == D004);
            assertEquals("segment is required", e.getMessage());
        }
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S02, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
        };
        final List<InputErrorEntity> errors = new LinkedList<>();
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertValid(invoice.getSeller());
        assertEquals(3, errors.size());
        for (var e : errors) {
            assertNull(e.getTrace());
            assertEquals(D002, e.getType());
            assertEquals(0, e.getOffset());
            assertEquals(SeverityType.ERROR, e.getSeverity());
            assertTrue(e.getType() == D001 || e.getType() == D002 || e.getType() == D004);
            assertEquals("size must be between 1 and 1", e.getMessage());
        }
    }

    @Test
    void testValid1() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
                create(S04, "2299", "Hardware", "1", "600", "500", "100"),
                create(S05, "1199", "Support", "Yes"),
                create(S05, "1199", "Duration", "one year"),
                create(S05, "2299", "Type", "Net"),
                create(S05, "2299", "Generation", "5th"),
        };
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(invoice);
        assertValid(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 2);
        }
    }

    @Test
    void testValid2() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
                create(S04, "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(invoice);
        assertNull(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 0);
        }
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final InputRowEntity detached = create(S05, "9999", "Warning", "Detached");
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
                create(S04, "2299", "Hardware", "1", "600", "500", "100"),
                detached,
        };
        final List<InputErrorEntity> errors = new LinkedList<>();
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertNull(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 0);
        }
        // Warning
        assertEquals(1, errors.size());
        {
            final InputErrorEntity e = errors.get(0);
            assertNotNull(e.getKey());
            assertEquals(detached, e.getKey().getRow());
            assertNull(e.getTrace());
            assertEquals(D005, e.getType());
            assertEquals(0, e.getOffset());
            assertEquals(SeverityType.WARNING, e.getSeverity());
            assertEquals(S05.getErrorCode(), e.getCode());
            assertEquals("segment is detached", e.getMessage());
        }
    }

}
