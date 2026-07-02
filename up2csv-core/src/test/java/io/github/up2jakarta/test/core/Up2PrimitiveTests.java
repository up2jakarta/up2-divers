package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.Test1Primitive;
import io.github.up2jakarta.test.core.misc.Test2Primitive;
import io.github.up2jakarta.test.core.misc.Test3Primitive;
import io.github.up2jakarta.test.core.misc.Test4Primitive;
import io.github.up2jakarta.test.impl.TermType;
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
    private final Up2Factory<TermType> factory;

    @Autowired
    Up2PrimitiveTests(Up2Factory<TermType> f) {
        this.factory = f;
    }

    @Test
    void testDefaultValues() throws BeanException {
        // Given
        final Up2Mapper<Test2Primitive, TermType> mapper = factory.mapper(Test2Primitive.class);
        // When
        final Test2Primitive bean = mapper.map();
        // Then
        assertNotNull(bean);
        assertFalse(bean.isABoolean());
        assertEquals(0, bean.getAByte());
        assertEquals('\0', bean.getAChar());
        assertEquals(0, bean.getAShort());
        assertEquals(0, bean.getAnInt());
        assertEquals(0L, bean.getALong());
        assertEquals(0.0F, bean.getAFloat());
        assertEquals(0.0, bean.getADouble());

    }

    @Test
    void testMapDefault() throws BeanException {
        // Given
        final Up2Mapper<Test1Primitive, TermType> mapper = factory.mapper(Test1Primitive.class);
        // When
        final Test1Primitive bean = mapper.map();
        // Then
        assertNotNull(bean);
        assertFalse(bean.isABoolean());
        assertEquals(1, bean.getAByte());
        assertEquals('2', bean.getAChar());
        assertEquals(3, bean.getAShort());
        assertEquals(4, bean.getAnInt());
        assertEquals(5L, bean.getALong());
        assertEquals(6.66F, bean.getAFloat());
        assertEquals(7.77D, bean.getADouble());
    }

    @Test
    void testKeepDefault() throws BeanException {
        // Given
        final Up2Mapper<Test4Primitive, TermType> mapper = factory.mapper(Test4Primitive.class);
        // When
        final Test4Primitive bean = mapper.map();
        // Then
        assertEquals(1.0F, bean.getAFloat());
        assertEquals(2.0D, bean.getADouble());
        assertEquals('*', bean.getAChar());
    }

    @Test
    void testUnmapDecimal() throws BeanException {
        // Given
        final Up2Flatter<Test3Primitive, TermType> format = factory.flatter(Test3Primitive.class);
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
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") int value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2Number", thrown.getMessage());
    }

    @Test
    void testBeanWithDecimal() {
        // GIVEN
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") double value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2Decimal", thrown.getMessage());
    }

    @Test
    void testBeanWithBoolean() {
        // GIVEN
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") boolean value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2Boolean", thrown.getMessage());
    }

    @Test
    void testBeanWithCharacter() {
        // GIVEN
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") char value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2Character", thrown.getMessage());
    }

    @Test
    void testBeanWithCodeList() {
        // GIVEN
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") CodeList<?> value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2CodeList", thrown.getMessage());
    }

    @Test
    void testBeanWithTemporal() {
        // GIVEN
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") LocalDate value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2Temporal", thrown.getMessage());
    }

    @Test
    void testBeanWithTemporalUnit() {
        // GIVEN
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") Period value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2TemporalAmount", thrown.getMessage());
    }

    @Test
    void testBeanWithByteArray() {
        // GIVEN
        final class Bean implements Segment {
            @Position(0)
            public @SuppressWarnings("unused") byte[] value;
        }
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.flatter(Bean.class));
        // THEN
        assertEquals(Bean.class, thrown.getSource());
        assertEquals("value", thrown.getLocator());
        assertEquals("should be annotated with @Up2Base64", thrown.getMessage());
    }

}
