package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.core.FullExporter;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.fmt.Fixed06Generator;
import io.github.up2jakarta.csv.fmt.Fixed13Generator;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.core.ModeType.FULL;
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
    void testSegregateNull() throws IOException {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        exporter.format(null, new Fixed13Generator(), r -> count.incrementAndGet());
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
        final AFullTester<R> tc = new AFullTester<>(invoice, rows);
        exporter.format(invoice, new Fixed06Generator(), tc::assertExists);
        tc.assertEmpty();
    }

}
