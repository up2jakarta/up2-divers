package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.impl.InputErrorEntity;
import io.github.up2jakarta.csv.impl.InputRowEntity;
import io.github.up2jakarta.csv.impl.InvoiceAggregator;
import io.github.up2jakarta.csv.impl.InvoiceSeparator;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.csv.misc.MapperException;
import io.github.up2jakarta.csv.test.sample.*;
import io.github.up2jakarta.csv.test.sample.Amount.Type;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.impl.BusinessType.*;
import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.csv.test.Tests.create;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class BusinessTests {

    private final InvoiceAggregator aggregator;
    private final InvoiceSeparator separator;

    @Autowired
    BusinessTests(InvoiceAggregator aggregator, InvoiceSeparator separator) {
        this.aggregator = aggregator;
        this.separator = separator;
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
        assertEquals(id, attribute.getItemId());
    }

    private static void assertValid(Amount amount) {
        assertNotNull(amount);
        assertNotNull(amount.getKey());
        assertNotNull(amount.getValue());
        assertNotNull(amount.getDescription());
    }

    private static void assertValid(Item item, int attributesSize) {
        assertNotNull(item);
        assertNotNull(item.getId());
        assertNotNull(item.getProduct());
        assertNotNull(item.getQuantity());
        assertNotNull(item.getGrossAmount());
        assertNotNull(item.getNetAmount());
        assertNotNull(item.getTaxAmount());
        assertEquals(attributesSize, item.getAttributes().size());
        for (final Map.Entry<String, Attribute> entry : item.getAttributes().entrySet()) {
            assertEquals(entry.getKey(), entry.getValue().getKey());
            assertValid(item.getId(), entry.getValue());
        }
    }

    private static void assertValid(Invoice invoice, int amountSize, int noteSize) {
        assertNotNull(invoice);
        assertNotNull(invoice.getReference());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getGrossAmount());
        assertNotNull(invoice.getNetAmount());
        assertNotNull(invoice.getTaxAmount());
        assertEquals(amountSize, invoice.getAmounts().size());
        for (final Map.Entry<Amount, Type> entry : invoice.getAmounts().entrySet()) {
            assertNotNull(entry.getValue());
            assertValid(entry.getKey());
        }
        assertEquals(noteSize, invoice.getNotes().length);
        for (final Note note : invoice.getNotes()) {
            assertNotNull(note.getContent());
            assertNotNull(note.getContent());
        }
    }

    private void assertExists(final String[] data, final List<InputRowEntity> rows) {
        assertTrue(data.length > 2);
        for (var it = rows.listIterator(); it.hasNext(); ) {
            final InputRowEntity row = it.next();
            final String type = row.getType().getCode();
            final String[] source = row.getColumns();
            if (type.equals(data[1]) && data.length == (source.length + 2)) {
                for (var i = 0; i < source.length; i++) {
                    if (!source[i].equals(data[i + 2])) {
                        break;
                    }
                }
                it.remove();
                return;
            }
        }
        throw new AssertionFailedError(Arrays.toString(data) + " does not exists");
    }

    @Test
    void testAggregateNull() throws BeanException {
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
    void testSegregateNull() throws BeanException {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        separator.format(null, r -> count.incrementAndGet());
        // Then
        assertEquals(0, count.get());
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
    void testCardinality1() {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S01, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When
        final MapperException e = assertThrows(MapperException.class, () -> aggregator.parse(rows, (i, r) -> i));
        assertEquals(0, e.getCauses().size());
        // Then
        assertEquals(D001, e.getDataType());
        assertEquals(0, e.getOffset());
        assertEquals(SeverityType.FATAL, e.getSeverityType());
        assertEquals(S01.getErrorCode(), e.getErrorCode());
        assertNotNull(e.getCause());
        assertNull(e.getCause().getCause());
        assertEquals("size must be between 1 and 1", e.getCause().getMessage());
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
        assertValid(invoice, 0, 0);
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
        assertValid(invoice, 0, 0);
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
        assertValid(invoice, 0, 0);
        assertNull(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 0);
        }
        // Warning
        assertEquals(1, errors.size());
        {
            final InputErrorEntity e = errors.getFirst();
            assertNotNull(e.getKey());
            assertEquals(detached, e.getKey().getRecord());
            assertNull(e.getTrace());
            assertEquals(D005, e.getType());
            assertEquals(0, e.getOffset());
            assertEquals(SeverityType.WARNING, e.getSeverity());
            assertEquals(S05.getErrorCode(), e.getCode());
            assertEquals("segment is detached", e.getMessage());
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
                create(S06, "90", "10.00", "Delivery fees"),
                create(S06, "99", "10.00", "Delivery discount"),
                create(S07, "DD", "Due date: 31/12/2025"),
                create(S07, "ES", "S.A. of capital 100.000$")
        };
        // When Parsing
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(invoice, 2, 2);
        assertValid(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 2);
        }
        // When Formating
        final List<InputRowEntity> source = new LinkedList<>(asList(rows));
        separator.format(invoice, d -> {
            assertEquals(invoice.getReference(), d[0]);
            assertExists(d, source);
        });
        assertEquals(0, source.size());
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
        // When Parsing
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(invoice, 0, 0);
        assertNull(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 0);
        }
        // When Formating
        final List<InputRowEntity> source = new LinkedList<>(asList(rows));
        separator.format(invoice, d -> {
            assertEquals(invoice.getReference(), d[0]);
            assertExists(d, source);
        });
        assertEquals(0, source.size());
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final InputRowEntity invalid = create(S05, "1199", "Support", null);
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        final List<InputErrorEntity> errors = new LinkedList<>();
        // When
        final Invoice invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        assertValid(invoice, 0, 0);
        assertValid(invoice.getBuyer());
        assertValid(invoice.getSeller());
        assertEquals(1, invoice.getItems().size());
        assertEquals(1, invoice.getItems().getFirst().getAttributes().size());
        // Warning
        assertEquals(1, errors.size());
        {
            final InputErrorEntity e = errors.getFirst();
            assertNotNull(e.getKey());
            assertEquals(invalid, e.getKey().getRecord());
            assertNull(e.getTrace());
            assertEquals(D005, e.getType());
            assertEquals(2 + 2, e.getOffset());
            assertEquals(SeverityType.ERROR, e.getSeverity());
            assertEquals(Errors.ERROR_VALIDATOR, e.getCode());
            assertEquals("must not be empty", e.getMessage());
        }
    }

}
