package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.Test1Primitive;
import io.github.up2jakarta.csv.core.misc.Test2Primitive;
import io.github.up2jakarta.csv.core.misc.Test3Primitive;
import io.github.up2jakarta.csv.core.misc.Test4Primitive;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class Up2PrimitiveTests {
    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2PrimitiveTests(Up2Factory<GroupType> f) {
        this.factory = f;
    }

    @Test
    void testDefaultValues() throws BeanException {
        // Given
        final Up2Mapper<Test2Primitive, GroupType> mapper = factory.build(Test2Primitive.class);
        // When
        final Test2Primitive bean = mapper.map();
        // Then
        assertNotNull(bean);
        assertFalse(bean.isABoolean());
        assertEquals(0, bean.getAByte());
        assertEquals(0, bean.getAShort());
        assertEquals(0, bean.getAnInt());
        assertEquals(0L, bean.getALong());
        assertEquals(0.0F, bean.getAFloat());
        assertEquals(0.0, bean.getADouble());

    }

    @Test
    void testMapDefault() throws BeanException {
        // Given
        final Up2Mapper<Test1Primitive, GroupType> mapper = factory.build(Test1Primitive.class);
        // When
        final Test1Primitive bean = mapper.map();
        // Then
        assertNotNull(bean);
        assertFalse(bean.isABoolean());
        assertEquals(1, bean.getAByte());
        assertEquals(2, bean.getAShort());
        assertEquals(3, bean.getAnInt());
        assertEquals(4L, bean.getALong());
        assertEquals(5.56F, bean.getAFloat());
        assertEquals(6.6667D, bean.getADouble());
    }

    @Test
    void testKeepDefault() throws BeanException {
        // Given
        final Up2Mapper<Test4Primitive, GroupType> mapper = factory.build(Test4Primitive.class);
        // When
        final Test4Primitive bean = mapper.map();
        // Then
        assertEquals(1.0F, bean.getAFloat());
        assertEquals(2.0D, bean.getADouble());
    }

    @Test
    void testUnmapDecimal() throws BeanException {
        // Given
        final Up2Flatter<Test3Primitive, GroupType> format = factory.format(Test3Primitive.class);
        final Test3Primitive bean = new Test3Primitive() {{
            setAFloat(3.140009F);
            setADouble(3.111409D);
        }};
        // When
        final String[] out = format.unmap(bean);
        // Then
        assertEquals("3.14", out[0]);
        assertEquals("3.1114", out[1]);
    }

    @Test
    void testBeanWithNumber() {
        // GIVEN
        @Access(AccessType.FIELD)
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") int value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("Up2PrimitiveTests.Bean[value] - should be annotated with @Up2Number", thrown.getMessage());
    }

    @Test
    void testBeanWithDecimal() {
        // GIVEN
        @Access(AccessType.FIELD)
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") double value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("Up2PrimitiveTests.Bean[value] - should be annotated with @Up2Decimal", thrown.getMessage());
    }

    @Test
    void testBeanWithBoolean() {
        // GIVEN
        @Access(AccessType.FIELD)
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") boolean value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("Up2PrimitiveTests.Bean[value] - should be annotated with @Up2Boolean", thrown.getMessage());
    }

    @Test
    void testBeanWithCodeList() {
        // GIVEN
        @Access(AccessType.FIELD)
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") CodeList<?> value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("Up2PrimitiveTests.Bean[value] - should be annotated with @Up2CodeList", thrown.getMessage());
    }

    @Test
    void testBeanWithTemporal() {
        // GIVEN
        @Access(AccessType.FIELD)
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") LocalDate value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("Up2PrimitiveTests.Bean[value] - should be annotated with @Up2Temporal", thrown.getMessage());
    }

    @Test
    void testBeanWithTemporalUnit() {
        // GIVEN
        @Access(AccessType.FIELD)
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") Period value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("Up2PrimitiveTests.Bean[value] - should be annotated with @Up2TemporalAmount", thrown.getMessage());
    }

    @Test
    void testBeanWithByteArray() {
        // GIVEN
        @Access(AccessType.FIELD)
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") byte[] value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.format(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("Up2PrimitiveTests.Bean[value] - should be annotated with @Up2Base64", thrown.getMessage());
    }

}
