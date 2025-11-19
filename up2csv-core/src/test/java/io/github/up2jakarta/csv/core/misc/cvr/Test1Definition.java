package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.csv.core.misc.lov.MeasurementUnitCode;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.csv.data.Segment;

import java.time.Duration;

@SuppressWarnings("unused")
public class Test1Definition implements Segment {

    public static final String D01 = "D01";
    public static final String D02 = "D02";

    @Position(0)
    @Up2CodeList
    @Definition(code = D01, value = "Measurement unit")
    private MeasurementUnitCode unit;

    @Position(1)
    @Up2TemporalAmount
    @Definition(code = D02, value = "Duration")
    private Duration duration;

    public MeasurementUnitCode getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnitCode unit) {
        this.unit = unit;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
