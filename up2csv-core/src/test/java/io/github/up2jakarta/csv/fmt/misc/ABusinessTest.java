package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.core.BusinessImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.hdl.PropertyFailureException;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.dto.*;
import io.github.up2jakarta.csv.impl.dto.Amount.Type;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.BeanException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.impl.GroupType.*;
import static io.github.up2jakarta.csv.impl.SegmentType.S01;
import static io.github.up2jakarta.csv.impl.SegmentType.S11;
import static org.junit.jupiter.api.Assertions.*;

abstract class ABusinessTest<T extends Invoice, R extends IRecord<SegmentType>, E extends IEvent<GroupType>> {

    private final ModeType mode;
    private final BusinessImporter<GroupType, SegmentType, T, R, E> importer;

    protected ABusinessTest(ModeType mode, BusinessImporter<GroupType, SegmentType, T, R, E> importer) {
        this.importer = importer;
        this.mode = mode;
    }

    protected static void assertValid(long id, Attribute attribute) {
        assertNotNull(attribute);
        assertNotNull(attribute.getKey());
        assertNotNull(attribute.getValue());
        assertEquals(id, attribute.getItemId());
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
            assertValid(item.getId(), entry.getValue());
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

    protected static void assertValid(ModeType mode, Invoice invoice, int amountSize, int noteSize) {
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

    protected abstract void checkValid1(R[] rows) throws BeanException, IOException;

    protected abstract void checkValid2(R[] rows) throws IOException;

    @SuppressWarnings("unchecked")
    protected final <A extends BusinessImporter<GroupType, SegmentType, T, R, E>> A get() {
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
        final PropertyFailureException e = assertThrows(PropertyFailureException.class, () -> importer.parse(rows, (i, r) -> i));
        assertEquals(0, e.toList().size());
        // Then
        assertNull(e.getOffset());
        assertEquals(S11.getDataType(), e.getType());
        assertEquals(S11.getEventLevel(), e.getLevel());
        assertEquals(S11.getEventCode(), e.getCode());
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
        for (final E e : errors) {
            if (e instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertNotNull(t.getKey().getRecord());
            } else if (e instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertNull(e.getOffset());
            assertEquals(S01.getDataType(), e.getType());
            assertEquals(S01.getEventLevel(), e.getLevel());
            assertEquals(S01.getEventCode(), e.getCode());
            assertEquals("cardinality must be 1 and only one", e.getMessage());
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
        for (var e : errors) {
            if (e instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
            } else if (e instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertNull(e.getOffset());
            assertEquals(SeverityType.ERROR, e.getLevel());
            if (e.getType() == D002 || e.getType() == D003) {
                assertEquals("cardinality must be 1 and only one", e.getMessage());
            } else {
                assertSame(D004, e.getType());
                assertEquals("cardinality must be greater than or equal to 1", e.getMessage());
            }
            assertNotNull(e.getCode());
        }
    }

    protected void checkCardinality3(SegmentType type, R[] rows) {
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
        assertEquals(2, errors.size());
        for (var e : errors) {
            if (e instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertEquals(type, t.getKey().getRecord().getType());
            } else if (e instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
                assertEquals(type, c.getRecord().getType());
            }
            assertEquals(D002, e.getType());
            assertNull(e.getOffset());
            assertEquals(type.getEventCode(), e.getCode());
            assertEquals(type.getEventLevel(), e.getLevel());
            assertEquals("cardinality must be 1 and only one", e.getMessage());
        }
    }

    protected void checkCardinality4(SegmentType type, SegmentType origin, R[] rows) {
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
        for (var e : errors) {
            if (e instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertEquals(origin, t.getKey().getRecord().getType());
            } else if (e instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
                assertEquals(origin, c.getRecord().getType());
            }
            assertEquals(D004, e.getType());
            assertNull(e.getOffset());
            assertEquals(type.getEventCode(), e.getCode());
            assertEquals(type.getEventLevel(), e.getLevel());
            assertEquals("cardinality must be greater than or equal to 1", e.getMessage());
        }
    }

    protected void checkDetached(R detached, R[] rows) {
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
            final E e = errors.getFirst();
            if (e instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertEquals(detached, t.getKey().getRecord());
            } else if (e instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertNull(e.getOffset());
            if (detached.getType() != null) {
                assertEquals(detached.getType().getEventLevel(), e.getLevel());
                assertEquals(detached.getType().getEventCode(), e.getCode());
                assertEquals(detached.getType().getDataType(), e.getType());
            } else {
                assertEquals(SeverityType.ERROR, e.getLevel());
                assertEquals(EC_COMPLIANCE, e.getCode());
                assertNull(e.getType());
            }
            assertEquals(DETACHED, e.getMessage());
        }
    }

    protected void checkValidation(R invalid, R[] rows) {
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
            final E e = errors.getFirst();
            if (e instanceof IBusinessEvent<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertEquals(invalid, t.getKey().getRecord());
            } else if (e instanceof IPropertyEvent<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertEquals(mode.getLength() + 2, e.getOffset());
            assertEquals(SeverityType.ERROR, e.getLevel());
            Assertions.assertEquals(EC_COMPLIANCE, e.getCode());
            assertEquals(invalid.getType().getDataType(), e.getType());
            assertEquals("must not be empty", e.getMessage());
        }
    }

}
