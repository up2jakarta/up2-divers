package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.clv.CountryConverter;
import io.github.up2jakarta.csv.test.clv.CurrencyCodeType;

@SuppressWarnings("unused")
public class Test3Converter implements Segment {

    @Position(0)
    @Up2Converter(CountryConverter.class)
    private CurrencyCodeType test;

    public CurrencyCodeType getTest() {
        return test;
    }

    public void setTest(CurrencyCodeType test) {
        this.test = test;
    }
}
