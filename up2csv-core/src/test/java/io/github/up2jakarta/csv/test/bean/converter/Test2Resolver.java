package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2CodeList;
import io.github.up2jakarta.csv.annotation.Up2TemporalAmount;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.MeasurementUnitCode;
import io.github.up2jakarta.xml.api.SeverityType;

import java.time.Duration;

@SuppressWarnings("unused")
public class Test2Resolver implements Segment {

    public static final String TU_P_007 = "TU-P007";
    public static final String TU_P_008 = "TU-P008";

    @Position(0)
    @Error(value = TU_P_007, severity = SeverityType.ERROR)
    @Up2CodeList
    private MeasurementUnitCode unit;

    @Position(1)
    @Error(value = TU_P_008, severity = SeverityType.WARNING)
    @Up2TemporalAmount
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
