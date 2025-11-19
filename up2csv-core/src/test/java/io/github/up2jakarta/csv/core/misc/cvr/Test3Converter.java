package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.csv.core.misc.lov.CountryConverter;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class Test3Converter implements Segment {

    @Position(value = 0, converter = @Up2Converter(CountryConverter.class))
    private CurrencyCodeType test;

    public CurrencyCodeType getTest() {
        return test;
    }

    public void setTest(CurrencyCodeType test) {
        this.test = test;
    }
}
