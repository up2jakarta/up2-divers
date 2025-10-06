package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.clv.CurrencyCodeType;
import io.github.up2jakarta.csv.test.clv.MeasurementUnitCode;
import io.github.up2jakarta.xml.api.SeverityType;

import java.time.Duration;

import static io.github.up2jakarta.csv.test.clv.MeasurementUnitConverter.EDI_R_20;

@SuppressWarnings("unused")
public class Test1Resolver implements Segment {

    @Position(0)
    @Up2CodeList
    private CurrencyCodeType currency;

    @Position(1)
    @Error(value = EDI_R_20, severity = SeverityType.ERROR)
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
