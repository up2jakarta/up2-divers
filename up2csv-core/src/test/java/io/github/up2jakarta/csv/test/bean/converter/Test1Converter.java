package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Converter;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.ext.DummyConverter;

@SuppressWarnings("unused")
public class Test1Converter implements Segment {

    @Position(0)
    @Converter(CurrencyConverter.class)
    private CurrencyCodeType test;

    @Position(1)
    @Converter(DummyConverter.class)
    private Integer other;

    public void setTest(CurrencyCodeType test) {
        this.test = test;
    }

    public void setOther(Integer other) {
        this.other = other;
    }
}
