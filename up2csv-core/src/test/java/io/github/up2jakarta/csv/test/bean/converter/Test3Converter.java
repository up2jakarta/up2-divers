package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Converter;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.CountryConverter;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;

@SuppressWarnings("unused")
public class Test3Converter implements Segment {

    @Position(0)
    @Converter(CountryConverter.class)
    private CurrencyCodeType test;

    public void setTest(CurrencyCodeType test) {
        this.test = test;
    }
}
