package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.test.core.misc.lov.CountryConverter;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;

public class Test3Converter implements Segment {

    @Position(value = 0, converter = @Up2Converter(CountryConverter.class))
    private CurrencyCodeType test;

}
