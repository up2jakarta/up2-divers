package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitExporter;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.impl.dto.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.fmt.misc.Tests.assertInvoice;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AUnitTest<T extends Invoice, R extends IRecord<SegmentType>, E extends IEvent<GroupType>> extends ABusinessTest<T, R, E> {

    protected final UnitImporter<GroupType, SegmentType, T, R, E> importer;
    protected final UnitExporter<GroupType, SegmentType, T> exporter;

    protected AUnitTest(UnitImporter<GroupType, SegmentType, T, R, E> importer) throws BeanException {
        super(ModeType.UNIT, importer);
        this.importer = importer;
        this.exporter = importer.toExporter();
    }

    @Test
    void testSegregateNull() throws IOException {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        exporter.format(null, (r) -> count.incrementAndGet());
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
        final AUnitTester<R> tc = new AUnitTester<>(rows);
        exporter.format(invoice, tc::assertExists);
        tc.assertEmpty();
    }

}
