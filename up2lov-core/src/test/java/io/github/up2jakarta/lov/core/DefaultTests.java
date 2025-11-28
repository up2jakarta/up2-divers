package io.github.up2jakarta.lov.core;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DefaultTests {

    @Test
    void testPrimitives() throws BeanException {
        // GIVEN
        // WHEN
        final Constructor<Primitive> constructor = Beans.getDeclaredConstructor(
                Primitive.class,
                boolean.class, byte.class, char.class, short.class, int.class, long.class, float.class, double.class
        );
        final Object[] arguments = Defaults.prototype(constructor);
        final Primitive bean = Beans.newInstance(constructor, arguments);
        //THEN
        assertFalse(bean.aBoolean);
        assertEquals(0, bean.aByte);
        assertEquals(0, bean.aShort);
        assertEquals(0, bean.anInt);
        assertEquals(0L, bean.aLong);
        assertEquals(0.0F, bean.aFloat);
        assertEquals(0.0, bean.aDouble);
        assertEquals('\u0000', bean.aChar);
    }

    static final class Primitive {
        private final boolean aBoolean;
        private final byte aByte;
        private final short aShort;
        private final int anInt;
        private final long aLong;
        private final float aFloat;
        private final double aDouble;
        private final double aChar;

        Primitive(boolean bl, byte bt, char c, short s, int i, long l, float f, double d) {
            this.aBoolean = bl;
            this.aByte = bt;
            this.aShort = s;
            this.anInt = i;
            this.aLong = l;
            this.aFloat = f;
            this.aDouble = d;
            this.aChar = c;
        }
    }
}
