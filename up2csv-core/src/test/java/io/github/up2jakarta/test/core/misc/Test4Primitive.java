package io.github.up2jakarta.test.core.misc;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Character;
import io.github.up2jakarta.csv.cfg.Up2Decimal;

public class Test4Primitive implements Segment {

    @Position(0)
    @Up2Decimal(2)
    private float aFloat = 1.0f;

    @Position(1)
    @Up2Decimal(4)
    private double aDouble = 2.0;

    @Position(2)
    @Up2Character
    private char aChar = '*';

    public float getAFloat() {
        return aFloat;
    }

    public double getADouble() {
        return aDouble;
    }

    public char getAChar() {
        return aChar;
    }
}
