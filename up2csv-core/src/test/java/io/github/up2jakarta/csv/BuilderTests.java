package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.ops.*;
import io.github.up2jakarta.csv.ops.misc.Dummy2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.csv.ops.misc.Tests.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BuilderTests {

    private final MapperFactory<GroupType> factory;
    private final SimpleCreator creator;

    @Autowired
    BuilderTests(MapperFactory<GroupType> factory, SimpleCreator creator) {
        this.factory = factory;
        this.creator = creator;
    }

    @Test
    void testFastSeparator() throws BeanException, IOException {
        // WHEN
        final FastSeparator<Invoice, GroupType, SegmentType> bean = factory.builder()
                .fast(Invoice.class)
                .fast(S01)
                .build();
        assertNotNull(bean);
        // THEN
        final String[] root = new String[]{"01", null, null, null, null, null};
        bean.format(new Invoice(), d -> assertArrayEquals(root, d));
    }

    @Test
    void testSimpleAggregator() throws BeanException, IOException {
        // WHEN
        final SimpleAggregator<Invoice, GroupType, SegmentType> aggregator = factory.builder()
                .fast(Invoice.class)
                .full(S01)
                .build();
        assertNotNull(aggregator);
        // THEN
        assertInvoice(aggregator, fastInvoice(S01));
    }

    @Test
    void testFastAggregator() throws BeanException, IOException {
        // WHEN
        final FastAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator = factory.builder()
                .fast(Invoice.class)
                .full(S01)
                .build(creator);
        assertNotNull(aggregator);
        // THEN
        assertInvoice(aggregator, fullInvoice(S01));
    }

    @Test
    void testFullSeparator() throws BeanException, IOException {
        // WHEN
        final FullSeparator<Invoice, GroupType, SegmentType> bean = factory.builder()
                .full(Invoice.class)
                .fast(S01)
                .build();
        assertNotNull(bean);
        // THEN
        final String[] root1 = new String[]{"00000001", "01", null, null, null, null, null};
        bean.format(new Invoice(), new Fixed08Generator(), d -> assertArrayEquals(root1, d));
        final String[] root2 = new String[]{"0000000000000001", "01", null, null, null, null, null};
        bean.format(new Invoice(), new Fixed16Generator(), d -> assertArrayEquals(root2, d));
    }

    @Test
    void testFullAggregator() throws BeanException, IOException {
        // WHEN
        final FullAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator = factory.builder()
                .full(Invoice.class)
                .full(S01)
                .build(creator, (r) -> 0);
        assertNotNull(aggregator);
        // THEN
        assertInvoice(aggregator, fullInvoice(S01));
    }

    @Test
    void testTypingException() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(Invoice.class)
                        .full(S11)
                        .build(creator, (r) -> 0)
        );
        assertNotNull(ex);
        // THEN
        assertEquals("Invoice[class] - Invalid business typing", ex.getMessage());
    }

    @Test
    void testRecursiveException() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(Dummy2.class)
                        .full(S31)
                        .build(creator, (r) -> 0)
        );
        assertNotNull(ex);
        // THEN
        assertEquals("SegmentType[31] - cyclic segment is not allowed: Dummy2 > Item2 > Dummy2", ex.getMessage());
    }

}
