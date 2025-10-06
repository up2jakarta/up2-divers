package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.core.ops.FastAggregator;
import io.github.up2jakarta.csv.core.ops.FastSeparator;
import io.github.up2jakarta.csv.core.ops.FullAggregator;
import io.github.up2jakarta.csv.core.ops.FullSeparator;
import io.github.up2jakarta.csv.impl.SimpleAggregator;
import io.github.up2jakarta.csv.ops.impl.*;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice1;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice2;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice3;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static io.github.up2jakarta.csv.ops.impl.SegmentType.*;
import static io.github.up2jakarta.csv.test.Tests.*;
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
    void testFastSeparator() throws BeanException {
        // WHEN
        final FastSeparator<Invoice1, GroupType, SegmentType> bean = factory.builder()
                .fast(Invoice1.class)
                .fast(S01)
                .build();
        assertNotNull(bean);
        // THEN
        final String[] root = new String[]{"01", null, null, null, null, null};
        bean.format(new Invoice1(), d -> assertArrayEquals(root, d));
    }

    @Test
    void testSimpleAggregator() throws BeanException {
        // WHEN
        final SimpleAggregator<Invoice1, GroupType, SegmentType> aggregator = factory.builder()
                .fast(Invoice1.class)
                .full(S01)
                .build();
        assertNotNull(aggregator);
        // THEN
        assertInvoice(aggregator, fastInvoice(S01));
    }

    @Test
    void testFastAggregator() throws BeanException {
        // WHEN
        final FastAggregator<Invoice1, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator = factory.builder()
                .fast(Invoice1.class)
                .full(S01)
                .build(creator);
        assertNotNull(aggregator);
        // THEN
        assertInvoice(aggregator, fullInvoice(S01));
    }

    @Test
    void testFullSeparator1() throws BeanException {
        // WHEN
        final FullSeparator<Invoice3, GroupType, SegmentType> bean = factory.builder()
                .full(Invoice3.class)
                .fast(S21)
                .build();
        assertNotNull(bean);
        // THEN
        final String[] root1 = new String[]{"00000001", "21", "TS3", null, null, null, null};
        bean.format(new Invoice3("TS3"), new AtomicInteger(0), d -> assertArrayEquals(root1, d));
        final String[] root2 = new String[]{"0000000000000001", "21", null, null, null, null, null};
        bean.format(new Invoice3(), new AtomicLong(0), d -> assertArrayEquals(root2, d));
    }

    @Test
    void testFullSeparator2() throws BeanException {
        // WHEN
        final FullSeparator<Invoice3, GroupType, SegmentType> bean = factory.builder()
                .full(Invoice3.class)
                .full(S21)
                .build();
        assertNotNull(bean);
        // THEN
        final String[] root1 = new String[]{"00000001", "21", "TS3", null, null, null, null};
        bean.format(new Invoice3("TS3"), new AtomicInteger(0), d -> assertArrayEquals(root1, d));
        final String[] root2 = new String[]{"0000000000000001", "21", null, null, null, null, null};
        bean.format(new Invoice3(), new AtomicLong(0), d -> assertArrayEquals(root2, d));
    }

    @Test
    void testFullAggregator() throws BeanException {
        // WHEN
        final FullAggregator<Invoice3, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator = factory.builder()
                .full(Invoice3.class)
                .full(S21)
                .build(creator, (r) -> 0);
        assertNotNull(aggregator);
        // THEN
        assertInvoice(aggregator, fullInvoice(S21));
    }

    @Test
    void testTyping1Exception() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(Invoice3.class)
                        .full(S11)
                        .build(creator, (r) -> 0)
        );
        assertNotNull(ex);
        // THEN
        assertEquals("Invoice3[class] - Invalid business typing", ex.getMessage());
    }

    @Test
    void testTyping2Exception() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .fast(Invoice3.class)
                        .full(S21)
                        .build(creator)
        );
        assertNotNull(ex);
        // THEN
        assertEquals("Invoice3[class] - @Truncated[value] must be  equals to 2", ex.getMessage());
    }

    @Test
    void testTyping3Exception() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(Invoice2.class)
                        .full(S11)
                        .build(creator, (r) -> 0)
        );
        assertNotNull(ex);
        // THEN
        assertEquals("Invoice2[class] - @Truncated[value] must be  equals to 3", ex.getMessage());
    }

}
