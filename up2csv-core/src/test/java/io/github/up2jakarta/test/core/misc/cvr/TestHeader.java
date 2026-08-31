package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.csv.data.Up2Header;
import io.github.up2jakarta.test.core.misc.lov.MeasurementUnitCode;

import java.time.Duration;

@SuppressWarnings("unused")
public class TestHeader implements Segment {

    public static final String D01 = "D01";
    public static final String D02 = "D02";

    @Position(0)
    @Up2CodeList
    @Up2Header(code = D01, name = "Measurement unit")
    private MeasurementUnitCode unit;

    @Position(1)
    @Up2TemporalAmount
    @Up2Header(code = D02, name = "Duration")
    private Duration duration;

    public MeasurementUnitCode getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnitCode unit) {
        this.unit = unit;
    }

}
