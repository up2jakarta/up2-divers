package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2CodeList;
import io.github.up2jakarta.csv.annotation.Up2TemporalAmount;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.MeasurementUnitCode;

import java.time.Duration;

import static io.github.up2jakarta.csv.extension.SeverityType.FATAL;
import static io.github.up2jakarta.csv.extension.SeverityType.WARNING;

@SuppressWarnings("unused")
public class Test2Resolver implements Segment {

    public static final String TU_P_007 = "TU-P007";
    public static final String TU_P_008 = "TU-P008";

    @Position(0)
    @Error(value = TU_P_007, severity = FATAL)
    @Up2CodeList
    private MeasurementUnitCode unit;

    @Position(1)
    @Error(value = TU_P_008, severity = WARNING)
    @Up2TemporalAmount
    private Duration duration;

    public void setUnit(MeasurementUnitCode unit) {
        this.unit = unit;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
