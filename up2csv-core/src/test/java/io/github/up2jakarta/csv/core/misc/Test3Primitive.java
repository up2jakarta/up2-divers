package io.github.up2jakarta.csv.core.misc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class Test3Primitive implements Segment {

    @Position(0)
    @Up2Decimal(2)
    private float aFloat;

    @Position(1)
    @Up2Decimal(4)
    private double aDouble;

    public float getAFloat() {
        return aFloat;
    }

    public void setAFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public double getADouble() {
        return aDouble;
    }

    public void setADouble(double aDouble) {
        this.aDouble = aDouble;
    }
}
