package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.test.core.misc.lov.MeasurementUnitCode;

import java.time.Duration;

import static io.github.up2jakarta.test.core.misc.lov.MeasurementUnitConverter.EDI_R_20;

public class Test1Resolver implements Segment {

    @Position(0)
    @Up2CodeList
    private CurrencyCodeType currency;

    @Position(1)
    @Error(value = EDI_R_20, level = SeverityType.ERROR)
    @Up2CodeList
    private MeasurementUnitCode unit;

    @Position(2)
    @Up2TemporalAmount
    private Duration duration;

    public CurrencyCodeType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCodeType currency) {
        this.currency = currency;
    }

    public MeasurementUnitCode getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnitCode unit) {
        this.unit = unit;
    }

}
