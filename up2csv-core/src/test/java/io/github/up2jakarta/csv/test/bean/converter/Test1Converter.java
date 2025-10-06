package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.clv.CurrencyCodeType;
import io.github.up2jakarta.csv.test.clv.CurrencyConverter;
import io.github.up2jakarta.csv.test.ext.DummyConverter;

@SuppressWarnings("unused")
public class Test1Converter implements Segment {

    @Position(0)
    @Up2Converter(CurrencyConverter.class)
    private CurrencyCodeType test;

    @Position(1)
    @Up2Converter(DummyConverter.class)
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
