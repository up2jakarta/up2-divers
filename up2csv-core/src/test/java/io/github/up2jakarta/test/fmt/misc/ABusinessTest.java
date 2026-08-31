package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.core.BusinessExporter;
import io.github.up2jakarta.csv.core.BusinessImporter;
import io.github.up2jakarta.csv.core.IMode;
import io.github.up2jakarta.csv.hdl.PropertyFailureException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.impl.dto.*;
import io.github.up2jakarta.test.impl.dto.Amount.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.core.BusinessImporter.DETACHED;
import static io.github.up2jakarta.csv.core.ModeType.NEAT;
import static io.github.up2jakarta.lov.SeverityType.*;
import static io.github.up2jakarta.test.impl.SegmentType.S09;
import static io.github.up2jakarta.test.impl.TermType.*;
import static org.junit.jupiter.api.Assertions.*;

abstract class ABusinessTest<T extends Invoice, R extends IRecord<SegmentType>, E extends IEvent<TermType>> {

    private final IMode mode;
    private final BusinessImporter<TermType, SegmentType, T, R, E> importer;

    protected ABusinessTest(IMode mode, BusinessImporter<TermType, SegmentType, T, R, E> importer) {
        this.importer = importer;
        this.mode = mode;
    }

    protected static void assertValid(Attribute attribute) {
        assertNotNull(attribute);
        assertNotNull(attribute.getKey());
        assertNotNull(attribute.getValue());
    }

    protected static void assertValid(Amount amount) {
        assertNotNull(amount);
        assertNotNull(amount.getKey());
        assertNotNull(amount.getValue());
        assertNotNull(amount.getDescription());
    }

    protected static void assertValid(Item item, int attributesSize) {
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
            assertValid(entry.getValue());
        }
    }

    protected static void assertValid(Party party) {
        assertNotNull(party);
        assertNotNull(party.getName());
        assertNotNull(party.getAddress());
        assertNotNull(party.getAddress().getCountry());
        assertNotNull(party.getAddress().getZipCode());
        assertNotNull(party.getAddress().getCity());
        assertNotNull(party.getAddress().getAddressLine1());
        assertNotNull(party.getAddress().getAddressLine2());
    }

    protected static void assertValid(IMode mode, Invoice invoice, int amountSize, int noteSize) {
        assertNotNull(invoice);
        Tests.assertReference(mode, invoice);
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getGrossAmount());
        assertNotNull(invoice.getNetAmount());
        assertNotNull(invoice.getTaxAmount());
        assertEquals(amountSize, invoice.getAmounts().size());
        // Third Parties
        if (invoice.getSeller() != null) {
            assertValid(invoice.getSeller());
        }
        if (invoice.getBuyer() != null) {
            assertValid(invoice.getBuyer());
        }
        if (invoice.getPayer().isPresent()) {
            assertValid(invoice.getPayer().get());
        }
        if (invoice.getPayee().isPresent()) {
            assertValid(invoice.getPayee().get());
        }
        // Amounts
        for (final Map.Entry<Amount, Type> entry : invoice.getAmounts().entrySet()) {
            assertNotNull(entry.getValue());
            assertValid(entry.getKey());
        }
        // Notes
        assertEquals(noteSize, invoice.getNotes().length);
        for (final Note note : invoice.getNotes()) {
            assertNotNull(note.getContent());
            assertNotNull(note.getContent());
        }
    }

    protected void assertValid(Invoice invoice) {
        assertValid(mode, invoice, 0, 0);
    }

    protected abstract void checkValid1(R[] rows) throws BeanException;

    protected abstract void checkValid2(R[] rows);

    @SuppressWarnings("unchecked")
    protected final <A extends BusinessImporter<TermType, SegmentType, T, R, E>> A get() {
        return (A) importer;
    }

    @Test
    @SuppressWarnings("ConstantValue")
    void testAggregateNull() {
        // Given
        final R[] rows = null;
        final List<E> errors = new LinkedList<>();
        // When
        final Invoice invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNull(invoice);
        assertEquals(0, errors.size());
    }

    private void assertCardinality3(SegmentType type, List<? extends IEvent<TermType>> errors) {
        for (final IEvent<TermType> error : errors) {
            if (error instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertEquals(type, t.getKey().getRecord().getType());
            } else if (error instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
                assertEquals(type, c.getRecord().getType());
            }
            assertEquals(D002, error.getType());
            assertNull(error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals("CSV-C02", error.getCode());
            assertEquals("cardinality must be 1 and only one", error.getMessage());
        }
    }

    private void assertCardinality4(SegmentType type, IEvent<TermType> error) {
        if (error instanceof IBusinessEvent<?, ?, ?> t) {
            assertNull(t.getTrace());
            assertEquals(type, t.getKey().getRecord().getType());
        } else if (error instanceof IPropertyEvent<?, ?> c) {
            assertNull(c.getCause().getCause());
            assertEquals(type, c.getRecord().getType());
        }
        assertEquals(D004, error.getType());
        assertNull(error.getOffset());
        assertEquals(ERROR, error.getLevel());
        assertEquals("CSV-C04", error.getCode());
        assertEquals("cardinality must be greater than or equal to 1", error.getMessage());
    }

    private void assertCardinality5(IEvent<TermType> error) {
        if (error instanceof IBusinessEvent<?, ?, ?> t) {
            assertNull(t.getTrace());
            assertEquals(SegmentType.S04, t.getKey().getRecord().getType());
        } else if (error instanceof IPropertyEvent<?, ?> c) {
            assertNull(c.getCause().getCause());
            assertEquals(SegmentType.S04, c.getRecord().getType());
        }
        assertEquals(D009, error.getType());
        assertNull(error.getOffset());
        assertEquals(WARNING, error.getLevel());
        assertEquals("CSV-C09", error.getCode());
        assertEquals("cardinality must be between 0 and 2", error.getMessage());
    }

    protected void checkEmpty(R[] rows) {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNull(invoice);
        assertEquals(0, errors.size());
    }

    protected void check1Cardinality1(R[] rows) {
        // When
        final PropertyFailureException e = assertThrows(
                PropertyFailureException.class,
                () -> importer.parse(rows, (i, r) -> i)
        );
        assertEquals(0, e.toList().size());
        // Then
        assertNull(e.getOffset());
        assertEquals(D001, e.getType());
        assertEquals(FATAL, e.getLevel());
        assertEquals("CSV-C01", e.getCode());
        assertNotNull(e.getCause());
        assertNull(e.getCause().getCause());
        assertEquals("cardinality must be 1 and only one", e.getCause().getMessage());
    }

    protected void check2Cardinality1(R[] rows) {
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        assertNull(invoice);
        assertEquals(2, errors.size());
        // Then
        for (final E error : errors) {
            if (error instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertNotNull(t.getKey().getRecord());
            } else if (error instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertNull(error.getOffset());
            assertEquals(D001, error.getType());
            assertEquals(FATAL, error.getLevel());
            assertEquals("CSV-C01", error.getCode());
            assertEquals("cardinality must be 1 and only one", error.getMessage());
        }
    }

    protected void checkCardinality2(R[] rows) {
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertNull(invoice.getSeller());
        assertNull(invoice.getBuyer());
        assertEquals(3, errors.size());
        for (final E error : errors) {
            if (error instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
            } else if (error instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertNull(error.getOffset());
            assertEquals(ERROR, error.getLevel());
            if (error.getType() == D002 || error.getType() == D003) {
                assertEquals("cardinality must be 1 and only one", error.getMessage());
            } else {
                assertSame(D004, error.getType());
                assertEquals("cardinality must be greater than or equal to 1", error.getMessage());
            }
            assertNotNull(error.getCode());
        }
    }

    protected void checkCardinality3(SegmentType type, R[] rows, BusinessExporter<TermType, SegmentType, T> exporter) {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertNull(invoice.getSeller());
        assertNotNull(invoice.getBuyer());
        assertEquals(2, errors.size());
        assertCardinality3(type, errors);
        // Then Validation
        final List<? extends IEvent<TermType>> events = exporter.validate(invoice);
        assertEquals(1, events.size());
        assertCardinality3(type, events);
    }

    protected void checkCardinality4(SegmentType type, R[] rows, BusinessExporter<TermType, SegmentType, T> exporter) {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertNotNull(invoice.getSeller());
        assertNotNull(invoice.getBuyer());
        assertEquals(1, errors.size());
        assertCardinality4(type, errors.getFirst());
        // Then Validation
        final List<? extends IEvent<TermType>> events = exporter.validate(invoice);
        assertEquals(1, events.size());
        assertCardinality4(type, events.getFirst());
    }

    protected void checkCardinality5(R[] rows, BusinessExporter<TermType, SegmentType, T> exporter) {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertNotNull(invoice.getSeller());
        assertNotNull(invoice.getBuyer());
        assertEquals(1, invoice.getItems().size());
        assertEquals(3, invoice.getItems().getFirst().getAttributes().size());
        assertEquals(1, errors.size());
        assertCardinality5(errors.getFirst());
        // Then Validation
        final List<? extends IEvent<TermType>> events = exporter.validate(invoice);
        assertEquals(1, events.size());
        assertCardinality5(events.getFirst());
    }

    protected void checkDetached(R detached, TermType type, R[] rows) {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice);
        assertNotNull(invoice.getSeller());
        assertNotNull(invoice.getBuyer());
        assertEquals(1, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 0);
        }
        // Warning
        assertEquals(1, errors.size());
        {
            final E error = errors.getFirst();
            if (error instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertEquals(detached, t.getKey().getRecord());
            } else if (error instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            if (detached.getType() != null) {
                if (detached.getType() == S09) {
                    assertNull(error.getOffset());
                    assertEquals(WARNING, error.getLevel());
                    assertEquals("CSV-C09", error.getCode());
                } else {
                    assertEquals(mode.getIndex(), error.getOffset());
                    assertEquals(WARNING, error.getLevel());
                    assertEquals(EC_COMPLIANCE, error.getCode());
                }
                assertEquals(type, error.getType());
            } else {
                assertEquals(mode.getIndex(), error.getOffset());
                assertEquals(ERROR, error.getLevel());
                assertEquals(EC_COMPLIANCE, error.getCode());
                assertNull(error.getType());
            }
            assertEquals(DETACHED, error.getMessage());
        }
    }

    protected void checkValidation(R invalid, TermType type, R[] rows) {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = importer.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        assertValid(invoice);
        assertNotNull(invoice.getSeller());
        assertNotNull(invoice.getBuyer());
        assertEquals(1, invoice.getItems().size());
        assertEquals(1, invoice.getItems().getFirst().getAttributes().size());
        // Warning
        assertEquals(1, errors.size());
        {
            final E error = errors.getFirst();
            if (error instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertEquals(invalid, t.getKey().getRecord());
            } else if (error instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            final int offset = (mode == NEAT) ? 1 : 2;
            assertEquals(mode.getLength() + offset, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            Assertions.assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(type, error.getType());
            assertEquals("must not be empty", error.getMessage());
        }
    }

}
