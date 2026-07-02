package io.github.up2jakarta.test.core.misc;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.*;

public class Test2Primitive implements Segment {

    @Position(0)
    @Up2Boolean
    private final boolean aBoolean;

    @Position(1)
    @Up2Number
    private final byte aByte;

    @Position(2)
    @Up2Character
    private final char aChar;

    @Position(3)
    @Up2Number
    private final short aShort;

    @Position(4)
    @Up2Number
    private final int anInt;

    @Position(5)
    @Up2Number
    private final long aLong;

    @Position(6)
    @Up2Decimal(2)
    private final float aFloat;

    @Position(7)
    @Up2Decimal(4)
    private final double aDouble;

    public Test2Primitive(boolean aBoolean, byte aByte, char aChar, short aShort, int anInt, long aLong, float aFloat, double aDouble) {
        this.aBoolean = aBoolean;
        this.aByte = aByte;
        this.aChar = aChar;
        this.aShort = aShort;
        this.anInt = anInt;
        this.aLong = aLong;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
    }

    public boolean isABoolean() {
        return aBoolean;
    }

    public byte getAByte() {
        return aByte;
    }

    public char getAChar() {
        return aChar;
    }

    public short getAShort() {
        return aShort;
    }

    public int getAnInt() {
        return anInt;
    }

    public long getALong() {
        return aLong;
    }

    public float getAFloat() {
        return aFloat;
    }

    public double getADouble() {
        return aDouble;
    }

}
