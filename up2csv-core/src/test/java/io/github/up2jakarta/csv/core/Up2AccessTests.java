package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.core.BSOperator.BId;
import io.github.up2jakarta.csv.core.hdl.SimpleCollector;
import io.github.up2jakarta.csv.core.misc.acs.*;
import io.github.up2jakarta.csv.core.misc.map.Default1Bean;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.github.up2jakarta.csv.core.Properties.assertUndefined;
import static io.github.up2jakarta.csv.core.Properties.assertValid;
import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.csv.core.misc.acs.Final1Segment.Source.CSV;
import static io.github.up2jakarta.csv.data.DataTypeResolver.dynamic;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2AccessTests {

    private final DataTypeResolver<?> resolver;
    private final Up2Factory<?> factory;

    @Autowired
    Up2AccessTests(Up2Factory<GroupType> factory) {
        this.resolver = factory.resolver;
        this.factory = factory;
    }

    @Test
    void valid1Bean() throws BeanException {
        // Given
        final Up2Mapper<Access1Bean, ?> mapper = factory.build(Access1Bean.class);
        final Up2Flatter<Access1Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access1Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid2Bean() throws BeanException {
        // Given
        final Up2Mapper<Access2Bean, ?> mapper = factory.build(Access2Bean.class);
        final Up2Flatter<Access2Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access2Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void valid3Bean() throws BeanException {
        // Given
        final Up2Mapper<Access31Bean, ?> mapper = factory.build(Access31Bean.class);
        final Up2Flatter<Access31Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access31Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void invalid3Bean() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Access32Bean.class));
        // Then
        assertEquals(Access32Bean.class, thrown.getSource());
        assertEquals("code", thrown.getLocator());
        assertEquals("Access32Bean[code] - getter not found", thrown.getMessage());
    }

    @Test
    void multipleCreators() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Final8Segment.class));
        // Then
        assertEquals(Final8Segment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("Final8Segment[class] - one and only one constructor must be annotated by @Creator", thrown.getMessage());
    }

    @Test
    void mixedProperties() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Final9Segment.class));
        // Then
        assertEquals(Final9Segment.class, thrown.getSource());
        assertEquals("class", thrown.getLocator());
        assertEquals("Final9Segment[class] - mix final and writable properties is not allowed", thrown.getMessage());
    }

    @Test
    void wrapSolution() throws BeanException {
        // Given
        final Up2Mapper<Final10Segment, ?> mapper = factory.build(Final10Segment.class);
        final Up2Flatter<Final10Segment, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10", "V1", "V2"};
        // When
        final Final10Segment bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        // Then
        assertEquals(10, bean.getKey());
        assertEquals("V1", bean.getCode());
        assertEquals("V2", bean.getValue());
        assertArrayEquals(data, out);
    }

    @Test
    void wrapValidation1() throws BeanException {
        // Given
        final Up2Mapper<Final10Segment, DynamicType> mapper = factory.build(Final10Segment.class, dynamic());
        final Up2Flatter<Final10Segment, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10", "V1", null};
        final SimpleCollector<DynamicType> collector = new SimpleCollector<>();
        // When
        final Final10Segment bean = mapper.map(collector, data);
        final String[] out = format.unmap(bean);
        final List<? extends IEvent<?>> events = collector.toList();
        // Then
        assertEquals(10, bean.getKey());
        assertEquals("V1", bean.getCode());
        assertNull(bean.getValue());
        assertArrayEquals(data, out);
        // Event
        assertEquals(1, events.size());
        final IEvent<?> event = events.getFirst();
        assertEquals(2, event.getOffset());
        assertEquals("must not be blank", event.getMessage());
    }

    @Test
    void wrapValidation2() throws BeanException {
        // Given
        final Up2Mapper<Final10Segment, DynamicType> mapper = factory.build(Final10Segment.class, dynamic());
        final Up2Flatter<Final10Segment, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"10", null, "V2"};
        final SimpleCollector<DynamicType> collector = new SimpleCollector<>();
        // When
        final Final10Segment bean = mapper.map(collector, data);
        final String[] out = format.unmap(bean);
        final List<? extends IEvent<?>> events = collector.toList();
        // Then
        assertEquals(10, bean.getKey());
        assertNull(bean.getCode());
        assertEquals("V2", bean.getValue());
        assertArrayEquals(data, out);
        // Event
        assertEquals(1, events.size());
        final IEvent<?> event = events.getFirst();
        assertEquals(1, event.getOffset());
        assertEquals("must not be blank", event.getMessage());
    }

    @Test
    void validAccessors() throws BeanException {
        // Given
        final Up2Mapper<Access4Bean, ?> mapper = factory.build(Access4Bean.class);
        final Up2Flatter<Access4Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validGenericBean() throws BeanException {
        // Given
        final Up2Mapper<Access5Bean, ?> mapper = factory.build(Access5Bean.class);
        final Up2Flatter<Access5Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB", "true"};
        // When
        final Access5Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validConstructor() throws BeanException {
        // Given
        final Up2Mapper<Access6Bean, ?> mapper = factory.build(Access6Bean.class);
        final Up2Flatter<Access6Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Access6Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord1Bean() throws BeanException {
        // Given
        final Up2Mapper<Record1Bean, ?> mapper = factory.build(Record1Bean.class);
        final Up2Flatter<Record1Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Record1Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord2Bean() throws BeanException {
        // Given
        final Up2Mapper<Record2Bean, ?> mapper = factory.build(Record2Bean.class);
        final Up2Flatter<Record2Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB", "Record"};
        // When
        final Record2Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord3Bean() throws BeanException {
        // Given
        final Up2Mapper<Record3Bean, ?> mapper = factory.build(Record3Bean.class);
        final Up2Flatter<Record3Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Record3Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord4Bean() throws BeanException {
        // Given
        final Up2Mapper<Record4Bean, ?> mapper = factory.build(Record4Bean.class);
        final Up2Flatter<Record4Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"AAB"};
        // When
        final Record4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validRecord5Bean() throws BeanException {
        // Given
        final Up2Mapper<Record5Bean, ?> mapper = factory.build(Record5Bean.class);
        final Up2Flatter<Record5Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{null, "Test"};
        // When
        final Record5Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(new String[]{"0", "Test"}, out);
    }

    @Test
    void validRecord6Bean() throws BeanException {
        // Given
        final Up2Mapper<Record6Bean, ?> mapper = factory.build(Record6Bean.class);
        final Up2Flatter<Record6Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{null, "Test"};
        // When
        final Record6Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(new String[]{"0", "Test"}, out);
    }

    @Test
    void validOptional1Bean() throws BeanException {
        final Up2Mapper<Optional1Bean, ?> mapper = factory.build(Optional1Bean.class);
        final Up2Flatter<Optional1Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional1Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        {
            // Given
            final String[] data = new String[]{null, null};
            // When
            final Optional1Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(new String[]{"0", null}, out);
            assertNotNull(bean.getContent());
        }
    }

    @Test
    void validOptional2Bean() throws BeanException {
        final Up2Mapper<Optional2Bean, ?> mapper = factory.build(Optional2Bean.class);
        final Up2Flatter<Optional2Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional2Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        // Given
        final String[] data = new String[]{"0", null};
        {
            // When
            final Optional2Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.content());
        }
        {
            // Given
            final Optional2Bean bean = new Optional2Bean(0, null);
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertNull(bean.content());
        }
    }

    @Test
    void validOptional3Bean() throws BeanException {
        final Up2Mapper<Optional3Bean, ?> mapper = factory.build(Optional3Bean.class);
        final Up2Flatter<Optional3Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional3Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }// Given
        final String[] data = new String[]{null, null};
        {
            // When
            final Optional3Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.getKey());
            assertNotNull(bean.getContent());
        }
        {
            // Given
            final Optional3Bean bean = new Optional3Bean();
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertNull(bean.getKey());
            assertNull(bean.getKey());
        }
    }

    @Test
    void validOptional4Bean() throws BeanException {
        final Up2Mapper<Optional4Bean, ?> mapper = factory.build(Optional4Bean.class);
        final Up2Flatter<Optional4Bean, ?> format = mapper.toFlatter();
        // Given
        final String[] data = new String[]{"1", "Test"};
        // When
        final Optional4Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
    }

    @Test
    void validOptional5Bean() throws BeanException {
        final Up2Mapper<Optional5Bean, ?> mapper = factory.build(Optional5Bean.class);
        final Up2Flatter<Optional5Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional5Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        // Given
        final String[] data = new String[]{"0", null};
        {
            // When
            final Optional5Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.getContent());
        }
        {
            // Given
            final Optional5Bean bean = new Optional5Bean();
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertNull(bean.getContent());
        }
    }

    @Test
    void validOptional6Bean() throws BeanException {
        final Up2Mapper<Optional6Bean, ?> mapper = factory.build(Optional6Bean.class);
        final Up2Flatter<Optional6Bean, ?> format = mapper.toFlatter();
        {
            // Given
            final String[] data = new String[]{"1", "Test"};
            // When
            final Optional6Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
        }
        // Given
        final String[] data = new String[]{"0", null};
        {
            // When
            final Optional6Bean bean = mapper.map(data);
            final String[] out = format.unmap(bean);
            format.validate(bean, of(WARNING));
            // Then
            assertArrayEquals(data, out);
            assertNotNull(bean.content());
        }
        {
            // Given
            final Optional6Bean bean = new Optional6Bean(0, null);
            // When
            final String[] out = format.unmap(bean);
            // Then
            assertArrayEquals(data, out);
            assertNull(bean.content());
        }
    }

    @Test
    void validOptional7Bean() throws BeanException {
        // Given
        final Up2Mapper<Optional7Bean, ?> mapper = factory.build(Optional7Bean.class);
        final Up2Flatter<Optional7Bean, ?> format = mapper.toFlatter();
        final String[] data = new String[]{"0", null};
        // When
        final Optional7Bean bean = mapper.map(data);
        final String[] out = format.unmap(bean);
        format.validate(bean, of(WARNING));
        // Then
        assertArrayEquals(data, out);
        assertNotNull(bean.getContent()); // Nullable
        assertTrue(bean.getContent().isEmpty());
    }

    @Test
    void validOptionalBusinessId() throws BeanException {
        // When
        final BId<Segment, Object> bid = factory.build(resolver, BIdOptionalBean.class).businessId;
        // Then
        assertValid(bid, new BIdOptionalBean());
    }

    @Test
    void validReadOnlyBusinessId() throws BeanException {
        // Given
        final BId5Bean bean = new BId5Bean(99);
        // When
        final BId<Segment, Object> bid = factory.format(resolver, BId5Bean.class).businessId;
        // Then
        assertTrue(bid.supports(null));
        assertTrue(bid.supports(BeanAccess.RO));
        assertFalse(bid.supports(BeanAccess.WO));
        assertEquals(99, bid.get(bean));
        assertEquals("99", bid.format(bean));
        assertThrows(AccessException.class, () -> bid.set(bean, 0));
    }

    @Test
    void validBusinessId1() throws BeanException {
        // Given
        final BId1Bean bean = new BId1Bean();
        // When
        final BId<Segment, Object> bid1 = factory.build(resolver, BId1Bean.class).businessId;
        final BId<Segment, Object> bid2 = factory.format(resolver, BId1Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId2() throws BeanException {
        // Given
        final BId2Bean bean = new BId2Bean();
        // When
        final BId<Segment, Object> bid1 = factory.build(resolver, BId2Bean.class).businessId;
        final BId<Segment, Object> bid2 = factory.format(resolver, BId2Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId3() throws BeanException {
        // Given
        final BId3Bean bean = new BId3Bean();
        // When
        final BId<Segment, Object> bid1 = factory.build(resolver, BId3Bean.class).businessId;
        final BId<Segment, Object> bid2 = factory.format(resolver, BId3Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId4() throws BeanException {
        // Given
        final BId4Bean bean = new BId4Bean(99);
        // When
        final BId<Segment, Object> bid1 = factory.build(resolver, BId4Bean.class).businessId;
        final BId<Segment, Object> bid2 = factory.format(resolver, BId4Bean.class).businessId;
        // Then
        assertValid(bid1, bean);
        assertValid(bid2, bean);
    }

    @Test
    void validBusinessId5() throws BeanException {
        // Given
        final Default1Bean bean = new Default1Bean();
        // When
        final BId<Segment, Object> bid1 = factory.build(resolver, Default1Bean.class).businessId;
        final BId<Segment, Object> bid2 = factory.format(resolver, Default1Bean.class).businessId;
        // Then
        assertUndefined(bid1, bean);
        assertUndefined(bid2, bean);
    }

    @Test
    void validBusinessId6() throws BeanException {
        // Given
        final Record1Bean bean = new Record1Bean(null);
        // When
        final BId<Segment, Object> bid1 = factory.build(resolver, Record1Bean.class).businessId;
        final BId<Segment, Object> bid2 = factory.format(resolver, Record1Bean.class).businessId;
        // Then
        assertUndefined(bid1, bean);
        assertUndefined(bid2, bean);
    }

    @Test
    void testFinal1() throws BeanException {
        // Given
        final Up2Flatter<Final1Segment, ?> format = factory.format(Final1Segment.class);
        final Up2Mapper<Final1Segment, ?> mapper = format.toMapper();
        // When
        final Final1Segment bean = mapper.map("TU");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertEquals(CSV, bean.getSource());
        assertArrayEquals(new String[]{"TU"}, data);
        Properties.assertFinal(mapper);
    }

    @Test
    void validFinal2() throws BeanException {
        // Given
        final Up2Flatter<Final2Segment, ?> format = factory.format(Final2Segment.class);
        final Up2Mapper<Final2Segment, ?> mapper = format.toMapper();
        // When
        final Final2Segment bean = mapper.map("TU", "Test");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test", bean.fragment.value);
        assertArrayEquals(new String[]{"TU", "Test"}, data);
    }

    @Test
    void validFinal3() throws BeanException {
        // Given
        final Up2Flatter<Final3Segment, ?> format = factory.format(Final3Segment.class);
        final Up2Mapper<Final3Segment, ?> mapper = format.toMapper();
        // When
        final Final3Segment bean = mapper.map("TU", "Test", "10");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertTrue(bean.fragment.rate.isPresent());
        assertTrue(bean.fragment.value.isPresent());
        assertEquals("Test", bean.fragment.value.get());
        assertEquals(10, bean.fragment.rate.get());
        assertArrayEquals(new String[]{"TU", "Test", "10"}, data);
    }

    @Test
    void validFinal4() throws BeanException {
        // Given
        final Up2Flatter<Final4Segment, ?> format = factory.format(Final4Segment.class);
        final Up2Mapper<Final4Segment, ?> mapper = format.toMapper();
        // When
        final Final4Segment bean = mapper.map("TU", "Test");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test", bean.fragment.value);
        assertArrayEquals(new String[]{"TU", "Test"}, data);
    }

    @Test
    void validFinal5() throws BeanException {
        // Given
        final Up2Flatter<Final5Segment, ?> format = factory.format(Final5Segment.class);
        final Up2Mapper<Final5Segment, ?> mapper = format.toMapper();
        // When
        final Final5Segment bean = mapper.map("TU", "Test1", "Test2");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test1", bean.fragment.value1);
        assertEquals("Test2", bean.fragment.value2);
        assertEquals(0, bean.fragment.rate1);
        assertEquals(0L, bean.fragment.rate2);
        assertArrayEquals(new String[]{"TU", "Test1", "Test2"}, data);
    }

    @Test
    void validFinal6() throws BeanException {
        // Given
        final Up2Flatter<Final6Segment, ?> format = factory.format(Final6Segment.class);
        final Up2Mapper<Final6Segment, ?> mapper = format.toMapper();
        // When
        final Final6Segment bean = mapper.map("TU", "Test");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code);
        assertNotNull(bean.fragment);
        assertEquals("Test", bean.fragment.value);
        assertEquals(bean, bean.fragment.getParent());
        assertArrayEquals(new String[]{"TU", "Test"}, data);
    }

    @Test
    void testFinal7() throws BeanException {
        // Given
        final Up2Flatter<Final7Segment, ?> format = factory.format(Final7Segment.class);
        final Up2Mapper<Final7Segment, ?> mapper = format.toMapper();
        // When
        final Final7Segment bean = mapper.map("TU");
        final String[] data = format.unmap(bean);
        // Then
        assertNotNull(bean);
        assertEquals("TU", bean.code());
        assertEquals(CSV, bean.source());
        assertArrayEquals(new String[]{"TU"}, data);
    }

}
