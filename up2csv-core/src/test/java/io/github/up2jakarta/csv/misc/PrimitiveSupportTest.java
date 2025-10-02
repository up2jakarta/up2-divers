package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.DataId;
import io.github.up2jakarta.csv.test.bean.Test1Primitive;
import io.github.up2jakarta.csv.test.bean.Test2Primitive;
import io.github.up2jakarta.csv.test.bean.Test3Primitive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class PrimitiveSupportTest {
    private final MapperFactory<DataId> factory;

    @Autowired
    PrimitiveSupportTest(MapperFactory<DataId> f) {
        this.factory = f;
    }

    @Test
    void testDefaultValues() throws BeanException {
        // Given
        final Mapper<Test2Primitive, DataId> mapper = factory.build(Test2Primitive.class);
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
        final Mapper<Test1Primitive, DataId> mapper = factory.build(Test1Primitive.class);
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
    void testUnmapDecimal() throws BeanException {
        // Given
        final Mapper<Test3Primitive, DataId> mapper = factory.build(Test3Primitive.class);
        final Test3Primitive bean = new Test3Primitive() {{
            setAFloat(3.140009F);
            setADouble(3.111409D);
        }};
        // When
        final String[] out = mapper.unmap(bean);
        // Then
        assertEquals("3.14", out[0]);
        assertEquals("3.1114", out[1]);
    }

}
