package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.lov.MeasurementUnitCode;

import java.time.Duration;

public class Test2Resolver implements Segment {

    public static final String TU_P_007 = "TU-P007";
    public static final String TU_P_008 = "TU-P008";

    @Position(0)
    @Error(value = TU_P_007, level = SeverityType.ERROR)
    @Up2CodeList
    private MeasurementUnitCode unit;

    @Position(1)
    @Error(value = TU_P_008, level = SeverityType.WARNING)
    @Up2TemporalAmount
    private Duration duration;

}
