package io.github.up2jakarta.test.core.misc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Boolean;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

public class Test2Primitive implements Segment {

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

    public boolean isABoolean() {
        return aBoolean;
    }

    public byte getAByte() {
        return aByte;
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
