package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.ext.DummyConverter;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.test.core.misc.lov.CurrencyConverter;

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

}
