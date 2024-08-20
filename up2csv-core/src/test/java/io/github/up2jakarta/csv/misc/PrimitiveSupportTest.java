package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.annotation.*;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Segment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class PrimitiveSupportTest {
    private final MapperFactory factory;

    @Autowired
    PrimitiveSupportTest(MapperFactory f) {
        this.factory = f;
    }

    @Test
    void testDefaultValues() throws BeanException {
        // Given
        final Mapper<Test2Primitive> mapper = factory.build(Test2Primitive.class);
        // When
        final Test2Primitive bean = mapper.map();
        // Then
        assertNotNull(bean);
        assertFalse(bean.aBoolean);
        assertEquals(0, bean.aByte);
        assertEquals(0, bean.aShort);
        assertEquals(0, bean.anInt);
        assertEquals(0L, bean.aLong);
        assertEquals(0.0F, bean.aFloat);
        assertEquals(0.0, bean.aDouble);

    }

    @Test
    void testDefaultAnnotation() throws BeanException {
        // Given
        final Mapper<Test1Primitive> mapper = factory.build(Test1Primitive.class);
        // When
        final Test1Primitive bean = mapper.map();
        // Then
        assertNotNull(bean);
        assertTrue(bean.aBoolean);
        assertEquals(1, bean.aByte);
        assertEquals(2, bean.aShort);
        assertEquals(3, bean.anInt);
        assertEquals(4L, bean.aLong);
        assertEquals(5.5F, bean.aFloat);
        assertEquals(6.6, bean.aDouble);

    }

    @SuppressWarnings("unused")
    public static class Test1Primitive implements Segment {

        @Position(0)
        @Up2Default("true")
        @Up2Boolean
        private boolean aBoolean;

        @Position(1)
        @Up2Default("1")
        @Up2Number
        private byte aByte;

        @Position(2)
        @Up2Default("2")
        @Up2Number
        private short aShort;

        @Position(3)
        @Up2Default("3")
        @Up2Number
        private int anInt;

        @Position(4)
        @Up2Default("4")
        @Up2Number
        private long aLong;

        @Position(5)
        @Up2Default("5.5")
        @Up2Decimal(2)
        private float aFloat;

        @Position(6)
        @Up2Default("6.6")
        @Up2Decimal(4)
        private double aDouble;

        public void setABoolean(boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public void setAByte(byte aByte) {
            this.aByte = aByte;
        }

        public void setAShort(short aShort) {
            this.aShort = aShort;
        }

        public void setAnInt(int anInt) {
            this.anInt = anInt;
        }

        public void setALong(long aLong) {
            this.aLong = aLong;
        }

        public void setAFloat(float aFloat) {
            this.aFloat = aFloat;
        }

        public void setADouble(double aDouble) {
            this.aDouble = aDouble;
        }
    }

    @SuppressWarnings("unused")
    public static class Test2Primitive implements Segment {

        @Position(0)
        @Up2Boolean
        private boolean aBoolean;

        @Position(1)
        @Up2Number
        private byte aByte;

        @Position(2)
        @Up2Number
        private short aShort;

        @Position(3)
        @Up2Number
        private int anInt;

        @Position(4)
        @Up2Number
        private long aLong;

        @Position(5)
        @Up2Decimal(2)
        private float aFloat;

        @Position(6)
        @Up2Decimal(4)
        private double aDouble;

        public void setABoolean(boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public void setAByte(byte aByte) {
            this.aByte = aByte;
        }

        public void setAShort(short aShort) {
            this.aShort = aShort;
        }

        public void setAnInt(int anInt) {
            this.anInt = anInt;
        }

        public void setALong(long aLong) {
            this.aLong = aLong;
        }

        public void setAFloat(float aFloat) {
            this.aFloat = aFloat;
        }

        public void setADouble(double aDouble) {
            this.aDouble = aDouble;
        }
    }

}
