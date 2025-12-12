package io.github.up2jakarta.test.core.misc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Boolean;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

public class Test1Primitive implements Segment {

    @Position(value = 0, defaultValue = "false")
    @Up2Boolean
    private boolean aBoolean;

    @Position(value = 1, defaultValue = "1")
    @Up2Number
    private byte aByte;

    @Position(value = 2, defaultValue = "2")
    @Up2Number
    private short aShort;

    @Position(value = 3, defaultValue = "3")
    @Up2Number
    private int anInt;

    @Position(value = 4, defaultValue = "4")
    @Up2Number
    private long aLong;

    @Position(value = 5, defaultValue = "5.555")
    @Up2Decimal(2)
    private float aFloat;

    @Position(value = 6, defaultValue = "6.66666")
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
