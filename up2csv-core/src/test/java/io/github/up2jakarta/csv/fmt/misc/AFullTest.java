package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.FullExporter;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.fmt.Fixed06Generator;
import io.github.up2jakarta.csv.fmt.Fixed13Generator;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.impl.dto.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.fmt.misc.Tests.assertInvoice;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AFullTest<T extends Invoice, R extends IFullRecord<SegmentType, ?>, E extends IEvent<GroupType>> extends ABusinessTest<T, R, E> {

    protected final FullImporter<GroupType, SegmentType, T, R, E> importer;
    protected final FullExporter<GroupType, SegmentType, T> exporter;

    protected AFullTest(FullImporter<GroupType, SegmentType, T, R, E> importer) throws BeanException {
        super(ModeType.FULL, importer);
        this.importer = importer;
        this.exporter = importer.toExporter();
    }

    @Test
    void testSegregateNull() throws IOException {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        exporter.format(null, new Fixed13Generator(), (r) -> count.incrementAndGet());
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
        assertNull(invoice.getPayee());
        assertNull(invoice.getPayer());
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
