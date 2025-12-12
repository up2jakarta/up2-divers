package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.core.FastExporter;
import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.core.ModeType.FAST;
import static io.github.up2jakarta.test.fmt.misc.Tests.assertInvoice;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AFastTest<T extends Invoice, R extends IFastRecord<SegmentType, ?>, E extends IEvent<GroupType>> extends ABusinessTest<T, R, E> {

    protected final FastImporter<GroupType, SegmentType, T, R, E> importer;
    protected final FastExporter<GroupType, SegmentType, T> exporter;

    protected AFastTest(FastImporter<GroupType, SegmentType, T, R, E> importer) throws BeanException {
        super(FAST, importer);
        this.importer = importer;
        this.exporter = importer.toExporter();
    }

    @Test
    void testSegregateNull() throws IOException {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        exporter.format(null, r -> count.incrementAndGet());
        // Then
        assertEquals(0, count.get());
    }

    protected void checkValid1(R[] rows) throws BeanException, IOException {
        assertInvoice(importer, rows);
    }

    protected void checkValid2(R[] rows) throws IOException {
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
        final AFastTester<R> tc = new AFastTester<>(invoice, rows);
        exporter.format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

}
