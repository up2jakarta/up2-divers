package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.data.Fixed06Generator;
import io.github.up2jakarta.csv.data.Fixed13Generator;
import io.github.up2jakarta.csv.data.FullExporter;
import io.github.up2jakarta.csv.data.FullImporter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Item;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.core.IMode.FULL;
import static io.github.up2jakarta.test.fmt.misc.Tests.assertInvoice;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AFullTest<T extends Invoice, R extends IFullRecord<SegmentType>, E extends IEvent<TermType>> extends ABusinessTest<T, R, E> {

    protected final FullImporter<TermType, SegmentType, T, R, E> importer;
    protected final FullExporter<TermType, SegmentType, T> exporter;

    protected AFullTest(FullImporter<TermType, SegmentType, T, R, E> importer) throws BeanException {
        super(FULL, importer);
        this.importer = importer;
        this.exporter = importer.toExporter();
    }

    @Test
    void testSegregateNull() {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        final Fixed13Generator uid = new Fixed13Generator();
        exporter.format(null, d -> {
            d[0] = uid.get();
            count.incrementAndGet();
        });
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
        final AFullTester<R> tc = new AFullTester<>(invoice, rows);
        final Fixed06Generator uid = new Fixed06Generator();
        exporter.format(invoice, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

}
