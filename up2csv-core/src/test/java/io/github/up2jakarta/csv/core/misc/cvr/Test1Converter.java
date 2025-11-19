package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyConverter;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class Test1Converter implements Segment {

    @Position(value = 0, converter = @Up2Converter(CurrencyConverter.class))
    private CurrencyCodeType test;

    @Position(value = 1, converter = @Up2Converter(DummyConverter.class))
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
