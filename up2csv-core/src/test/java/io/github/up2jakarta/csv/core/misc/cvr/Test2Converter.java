package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyConverter;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;

@SuppressWarnings("unused")
public class Test2Converter implements Segment {

    public static final String TU_P_004 = "TU-P004";
    public static final String TU_P_006 = "TU-P006";

    @Position(value = 0, converter = @Up2Converter(CurrencyConverter.class))
    @Error(value = TU_P_004, level = SeverityType.WARNING)
    private CurrencyCodeType test;

    @Position(value = 1, converter = @Up2Converter(DummyConverter.class))
    @Error(value = TU_P_006, level = SeverityType.ERROR)
    private Integer other;

    public CurrencyCodeType getTest() {
        return test;
    }

    public void setTest(CurrencyCodeType test) {
        this.test = test;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }
}
