package io.github.up2jakarta.csv.test;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IErrorCause;
import io.github.up2jakarta.csv.api.hdl.IErrorEntity;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Errors;
import io.github.up2jakarta.csv.core.ops.FastAggregator;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.impl.FatalException;
import io.github.up2jakarta.csv.ops.impl.GroupType;
import io.github.up2jakarta.csv.ops.impl.SegmentType;
import io.github.up2jakarta.csv.ops.impl.dto.*;
import io.github.up2jakarta.csv.ops.impl.dto.Amount.Type;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.ops.impl.GroupType.*;
import static io.github.up2jakarta.csv.ops.impl.SegmentType.S11;
import static io.github.up2jakarta.csv.test.Tests.assertInvoice;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public abstract class ABusinessTest<T extends Invoice, R extends IRecord<SegmentType>, E extends IError<GroupType>> {

    protected final FastAggregator<T, GroupType, SegmentType, R, E> aggregator;

    protected ABusinessTest(FastAggregator<T, GroupType, SegmentType, R, E> aggregator) {
        this.aggregator = aggregator;
    }

    protected static void assertReference(Invoice invoice) {
        if (invoice.getRecord() instanceof IRecordEntity<?> source) {
            assertEquals(source.getReference(), invoice.getReference());
        } else if (invoice instanceof Invoice1) {
            assertNotNull(invoice.getReference());
        } else {
            assertNull(invoice.getReference());
        }
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

    public static void assertValid(Item item, int attributesSize) {
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

    public static void assertValid(Party party) {
        assertNotNull(party);
        assertNotNull(party.getName());
        assertNotNull(party.getAddress());
        assertNotNull(party.getAddress().getCountry());
        assertNotNull(party.getAddress().getZipCode());
        assertNotNull(party.getAddress().getCity());
        assertNotNull(party.getAddress().getAddressLine1());
        assertNotNull(party.getAddress().getAddressLine2());
    }

    public static void assertValid(Invoice invoice, int amountSize, int noteSize) {
        assertNotNull(invoice);
        assertReference(invoice);
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

    @Test
    void testAggregateNull() throws BeanException {
        // Given
        final R[] rows = null;
        final List<E> errors = new LinkedList<>();
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
        aggregator.format(null, (r) -> count.incrementAndGet());
        // Then
        assertEquals(0, count.get());
    }

    protected void testEmpty(R[] rows) throws BeanException {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNull(invoice);
        assertEquals(0, errors.size());
    }

    protected void fastCardinality1(R[] rows) {
        // When
        final FatalException e = assertThrows(FatalException.class, () -> aggregator.parse(rows, (i, r) -> i));
        assertEquals(0, e.getCauses().size());
        // Then
        assertEquals(1, e.getOffset());
        assertEquals(S11.getBusinessType(), e.getDataType());
        assertEquals(S11.getErrorLevel(), e.getSeverity());
        assertEquals(S11.getErrorCode(), e.getCode());
        assertNotNull(e.getCause());
        assertNull(e.getCause().getCause());
        assertEquals("cardinality must be 1 and only one", e.getCause().getMessage());
    }

    protected void testCardinality1(SegmentType type, R[] rows) throws BeanException {
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        assertNull(invoice);
        assertEquals(2, errors.size());
        // Then
        for (final E e : errors) {
            if (e instanceof IErrorEntity<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertNotNull(t.getKey().getRecord());
                assertNotNull(t.getKey().getOrder());
            } else if (e instanceof IErrorCause<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertEquals(1, e.getOffset());
            assertEquals(type.getBusinessType(), e.getType());
            assertEquals(type.getErrorLevel(), e.getSeverity());
            assertEquals(type.getErrorCode(), e.getCode());
            assertEquals("cardinality must be 1 and only one", e.getMessage());
        }
    }

    protected void testCardinality2(R[] rows) throws BeanException {
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice, 0, 0);
        assertEquals(2, errors.size());
        for (var e : errors) {
            if (e instanceof IErrorEntity<?, ?, ?> t) {
                assertNull(t.getTrace());
            } else if (e instanceof IErrorCause<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertEquals(1, e.getOffset());
            assertEquals(SeverityType.ERROR, e.getSeverity());
            if (e.getType() == D002) {
                assertEquals("cardinality must be 1 and only one", e.getMessage());
            } else {
                assertSame(D004, e.getType());
                assertEquals("cardinality must be greater than or equal to 1", e.getMessage());
            }
            assertNotNull(e.getCode());
        }
    }

    protected void testCardinality3(SegmentType type, R[] rows) throws BeanException {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice, 0, 0);
        assertValid(invoice.getSeller());
        assertEquals(2, errors.size());
        for (var e : errors) {
            if (e instanceof IErrorEntity<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertEquals(type, t.getKey().getRecord().getType());
            } else if (e instanceof IErrorCause<?, ?> c) {
                assertNull(c.getCause().getCause());
                assertEquals(type, c.getRecord().getType());
            }
            assertEquals(D002, e.getType());
            assertEquals(1, e.getOffset());
            assertEquals(type.getErrorCode(), e.getCode());
            assertEquals(type.getErrorLevel(), e.getSeverity());
            assertEquals("cardinality must be 1 and only one", e.getMessage());
        }
    }

    protected void testCardinality4(SegmentType type, SegmentType origin, R[] rows) throws BeanException {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = aggregator.parse(rows, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertValid(invoice, 0, 0);
        assertValid(invoice.getSeller());
        assertEquals(1, errors.size());
        for (var e : errors) {
            if (e instanceof IErrorEntity<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertEquals(origin, t.getKey().getRecord().getType());
            } else if (e instanceof IErrorCause<?, ?> c) {
                assertNull(c.getCause().getCause());
                assertEquals(origin, c.getRecord().getType());
            }
            assertEquals(D004, e.getType());
            assertEquals(1, e.getOffset());
            assertEquals(type.getErrorCode(), e.getCode());
            assertEquals(type.getErrorLevel(), e.getSeverity());
            assertEquals("cardinality must be greater than or equal to 1", e.getMessage());
        }
    }

    protected void testDetached(R detached, R[] rows) throws BeanException {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = aggregator.parse(rows, (i, r) -> {
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
            final E e = errors.getFirst();
            if (e instanceof IErrorEntity<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertEquals(detached, t.getKey().getRecord());
            } else if (e instanceof IErrorCause<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertEquals(D005, e.getType());
            assertEquals(1, e.getOffset());
            assertEquals(detached.getType().getErrorLevel(), e.getSeverity());
            assertEquals(detached.getType().getErrorCode(), e.getCode());
            assertEquals(DataType.DETACHED, e.getMessage());
        }
    }

    protected void testValid1(R[] rows) throws BeanException {
        assertInvoice(aggregator, rows);
    }

    protected void testValid2(R[] rows) throws BeanException {
        // When Parsing
        final T invoice = aggregator.parse(rows, (i, r) -> {
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
        final AFastTester<R> tc = new AFastTester<>(invoice, rows);
        aggregator.format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

    protected void testValidation(R invalid, R[] rows) throws BeanException {
        // Given
        final List<E> errors = new LinkedList<>();
        // When
        final T invoice = aggregator.parse(rows, (i, r) -> {
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
            final E e = errors.getFirst();
            if (e instanceof IErrorEntity<?, ?, ?> t) {
                assertNull(t.getTrace());
                assertNotNull(t.getKey());
                assertEquals(invalid, t.getKey().getRecord());
            } else if (e instanceof IErrorCause<?, ?> c) {
                assertNull(c.getCause().getCause());
            }
            assertEquals(2 + 2, e.getOffset());
            assertEquals(SeverityType.ERROR, e.getSeverity());
            Assertions.assertEquals(Errors.ERROR_VALIDATOR, e.getCode());
            assertEquals(invalid.getType().getBusinessType(), e.getType());
            assertEquals("must not be empty", e.getMessage());
        }
    }

}
