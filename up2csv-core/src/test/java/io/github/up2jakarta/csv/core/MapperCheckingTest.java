package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.test.bean.mapper.*;
import io.github.up2jakarta.csv.test.bean.mapper.InnerSegment.InnerFragment;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.AddressSegment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class MapperCheckingTest {

    private final MapperFactory factory;
    private final Mapper<ValidBean> mapper;

    @Autowired
    MapperCheckingTest(MapperFactory factory) throws BeanException {
        this.factory = factory;
        this.mapper = factory.build(ValidBean.class);
    }

    @Test
    void testLocalClass() {
        //Given
        class LocalSegment implements Segment {
        }
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(LocalSegment.class));
        // THEN
        assertEquals(LocalSegment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("local class is not allowed", thrown.getMessage());
    }

    @Test
    void testAbstractClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(AddressSegment.class));
        // THEN
        assertEquals(AddressSegment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("abstract class is not allowed", thrown.getMessage());
    }

    @Test
    void testInterface() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Segment.class));
        // THEN
        assertEquals(Segment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("interface is not allowed", thrown.getMessage());
    }

    @Test
    void testGenericClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test8Segment.class));
        // THEN
        assertEquals(Test8Segment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("generic class is not allowed", thrown.getMessage());
    }

    @Test
    void testInnerClass() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(InnerSegment.class));
        // THEN
        assertEquals(InnerFragment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testInnerSegment() {
        // When
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(InnerFragment.class));
        // THEN
        assertEquals(InnerFragment.class, thrown.getBeanType());
        assertEquals("class", thrown.getAttribute());
        assertEquals("inner class is not allowed", thrown.getMessage());
    }

    @Test
    void testMapValidRecord() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(RecordBean.class));
        // THEN
        assertEquals(RecordBean.class, thrown.getBeanType());
        assertEquals("id", thrown.getAttribute());
        assertEquals("must not be final", thrown.getMessage());
        assertEquals("RecordBean[id] causes an error: must not be final", thrown.getFormattedMessage());
    }

    @Test
    void testBeanWithInteger() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(BeanWithInteger.class));
        // THEN
        assertEquals(BeanWithInteger.class, thrown.getBeanType());
        assertEquals("id", thrown.getAttribute());
        assertEquals("type should be String", thrown.getMessage());
        assertEquals("BeanWithInteger[id] causes an error: type should be String", thrown.getFormattedMessage());
    }

    @Test
    @SuppressWarnings("unused")
    void testFragment() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test1Segment.class));
        // THEN
        assertEquals(Test1Segment.class, thrown.getBeanType());
        assertEquals("p", thrown.getAttribute());
        assertEquals("should be annotated by @Fragment", thrown.getMessage());
        assertEquals("Test1Segment[p] causes an error: should be annotated by @Fragment", thrown.getFormattedMessage());
    }

    @Test
    void testFragmentOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test2Segment.class));
        // THEN
        assertEquals(Test2Segment.class, thrown.getBeanType());
        assertEquals("p", thrown.getAttribute());
        assertEquals("@Fragment[value] should be positive", thrown.getMessage());
        assertEquals("Test2Segment[p] causes an error: @Fragment[value] should be positive", thrown.getFormattedMessage());
    }

    @Test
    void testPositionOffset() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test3Segment.class));
        // THEN
        assertEquals(Test3Segment.class, thrown.getBeanType());
        assertEquals("p", thrown.getAttribute());
        assertEquals("@Position[value] should be positive", thrown.getMessage());
        assertEquals("Test3Segment[p] causes an error: @Position[value] should be positive", thrown.getFormattedMessage());
    }

    @Test
    void testFieldName() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test4Segment.class));
        // THEN
        assertEquals(Test4Segment.class, thrown.getBeanType());
        assertEquals("Upper", thrown.getAttribute());
        assertEquals("must starts with an lowercase character", thrown.getMessage());
        assertEquals("Test4Segment[Upper] causes an error: must starts with an lowercase character", thrown.getFormattedMessage());
    }

    @Test
    void testFieldVisibility() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test5Segment.class));
        // THEN
        assertEquals(Test5Segment.class, thrown.getBeanType());
        assertEquals("publicField", thrown.getAttribute());
        assertEquals("should be private or protected", thrown.getMessage());
        assertEquals("Test5Segment[publicField] causes an error: should be private or protected", thrown.getFormattedMessage());
    }

    @Test
    void testFieldFinal() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test6Segment.class));
        // THEN
        assertEquals(Test6Segment.class, thrown.getBeanType());
        assertEquals("finalField", thrown.getAttribute());
        assertEquals("must not be final", thrown.getMessage());
        assertEquals("Test6Segment[finalField] causes an error: must not be final", thrown.getFormattedMessage());
    }

    @Test
    void testFieldStatic() {
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test7Segment.class));
        // THEN
        assertEquals(Test7Segment.class, thrown.getBeanType());
        assertEquals("staticField", thrown.getAttribute());
        assertEquals("must not be static", thrown.getMessage());
        assertEquals("Test7Segment[staticField] causes an error: must not be static", thrown.getFormattedMessage());
    }

    @Test
    void testMappingBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        // WHEN
        final ValidBean bean = mapper.map(id, name);
        final Property<?>[] fields = mapper.properties;
        // THEN
        assertEquals(2, fields.length);
        {
            final List<String> names = Stream.of(fields).map(f -> f.getField().getName()).toList();
            assertTrue(names.contains("id"));
            assertTrue(names.contains("name"));
        }
        for (final Property<?> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty f = (StringProperty) p;
            f.getField().setAccessible(true);
            assertEquals("V", f.getField().get(bean));
            // When
            f.setValue(bean, "Test");
            // Then
            assertEquals("Test", f.getField().get(bean));
        }
    }

    @Test
    void testMappingNoOrderBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<NoOrderBean> mapper = factory.build(NoOrderBean.class);
        // WHEN
        final NoOrderBean bean = mapper.map(id, name);
        final Property<?>[] fields = mapper.properties;
        // THEN
        assertEquals(2, fields.length);
        {
            final List<String> names = Stream.of(fields).map(f -> f.getField().getName()).toList();
            assertTrue(names.contains("id"));
            assertTrue(names.contains("name"));
        }
        for (final Property<?> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty f = (StringProperty) p;
            f.getField().setAccessible(true);
            assertEquals("V", f.getField().get(bean));
            // When
            f.setValue(bean, "Test");
            // Then
            assertEquals("Test", f.getField().get(bean));
        }
    }

    @Test
    void testMappingNoPositionBean() throws BeanException, IllegalAccessException {
        // GIVEN
        final String id = "V";
        final String name = "V";
        final Mapper<NoPositionBean> mapper = factory.build(NoPositionBean.class);
        // WHEN
        final NoPositionBean bean = mapper.map(id, name);
        final Property<?>[] fields = mapper.properties;
        // THEN
        assertEquals(2, fields.length);
        {
            final List<String> names = Stream.of(fields).map(f -> f.getField().getName()).toList();
            assertTrue(names.contains("id"));
            assertTrue(names.contains("name"));
            assertFalse(names.contains("ignored"));
        }
        for (final Property<?> p : fields) {
            assertInstanceOf(StringProperty.class, p);
            final StringProperty f = (StringProperty) p;
            f.getField().setAccessible(true);
            assertEquals("V", f.getField().get(bean));
            // When
            f.setValue(bean, "Test");
            // Then
            assertEquals("Test", f.getField().get(bean));
        }
    }

}
