package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IMessRecord;
import io.github.up2jakarta.csv.core.MessExporter;
import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Item;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.core.ModeType.MESS;
import static io.github.up2jakarta.test.fmt.misc.Tests.assertInvoice;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AMessTest<T extends Invoice, R extends IMessRecord<SegmentType>, E extends IEvent<TermType>> extends ABusinessTest<T, R, E> {

    protected final MessImporter<TermType, SegmentType, T, R, E> importer;
    protected final MessExporter<TermType, SegmentType, T> exporter;

    protected AMessTest(MessImporter<TermType, SegmentType, T, R, E> importer) throws BeanException {
        super(MESS, importer);
        this.importer = importer;
        this.exporter = importer.toExporter();
    }

    @Test
    void testSegregateNull() {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        exporter.format(null, r -> count.incrementAndGet());
        // Then
        assertEquals(0, count.get());
    }

    protected void checkValid1(R[] rows) throws BeanException {
        assertInvoice(importer, rows);
    }

    protected void checkValid2(R[] rows) {
        // When Parsing
        final T invoice = importer.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(invoice);
        assertTrue(invoice.getPayee().isEmpty());
        assertTrue(invoice.getPayer().isEmpty());
        assertNotNull(invoice.getSeller());
        assertNotNull(invoice.getBuyer());
        assertEquals(2, invoice.getItems().size());
        for (final Item item : invoice.getItems()) {
            assertValid(item, 0);
        }
        // When Formating
        final AMessTester<R> tc = new AMessTester<>(invoice, rows);
        exporter.format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

}
