package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2CodeList;
import io.github.up2jakarta.csv.annotation.Up2TemporalAmount;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.MeasurementUnitCode;

import java.time.Duration;

import static io.github.up2jakarta.csv.extension.SeverityType.FATAL;
import static io.github.up2jakarta.csv.test.codelist.MeasurementUnitConverter.EDI_R_20;

@SuppressWarnings("unused")
public class Test1Resolver implements Segment {

    @Position(0)
    @Up2CodeList
    private CurrencyCodeType currency;

    @Position(1)
    @Up2CodeList(@Error(value = EDI_R_20, severity = FATAL))
    private MeasurementUnitCode unit;

    @Position(2)
    @Up2TemporalAmount
    private Duration duration;

    public void setCurrency(CurrencyCodeType currency) {
        this.currency = currency;
    }

    public void setUnit(MeasurementUnitCode unit) {
        this.unit = unit;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
