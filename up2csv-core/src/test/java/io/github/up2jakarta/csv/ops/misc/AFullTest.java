package io.github.up2jakarta.csv.ops.misc;

import io.github.up2jakarta.csv.api.hdl.IErrorEntity;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.impl.dto.Item;
import io.github.up2jakarta.csv.ops.Fixed08Generator;
import io.github.up2jakarta.csv.ops.FullAggregator;
import io.github.up2jakarta.csv.ops.ModeType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.ops.misc.Tests.assertInvoice;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AFullTest<T extends Invoice, R extends IRecordEntity<SegmentType, ?, ?>, E extends IErrorEntity<R, ?, GroupType>> extends ABusinessTest<T, R, E> {

    protected final FullAggregator<T, GroupType, SegmentType, R, E> aggregator;

    protected AFullTest(FullAggregator<T, GroupType, SegmentType, R, E> aggregator) {
        super(ModeType.FULL, aggregator);
        this.aggregator = aggregator;
    }

    @Test
    void testSegregateNull() throws BeanException, IOException {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        // When
        aggregator.format(null, new Fixed08Generator(), (r) -> count.incrementAndGet());
        // Then
        assertEquals(0, count.get());
    }

    protected void checkValid1(R[] rows) throws BeanException, IOException {
        assertInvoice(aggregator, rows);
    }

    protected void checkValid2(R[] rows) throws BeanException, IOException {
        // When Parsing
        final T invoice = aggregator.parse(rows, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        // Then
        assertValid(invoice, 0, 0);
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
        aggregator.format(invoice, new Fixed08Generator(), tc::assertExists);
        tc.assertEmpty();
    }

}
