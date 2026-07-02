package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.ext.DummyConverter;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.test.core.misc.lov.CurrencyConverter;

public class Test2Converter implements Segment {

    public static final String TU_P_004 = "TU-P004";
    public static final String TU_P_006 = "TU-P006";

    @Position(value = 0, converter = @Up2Converter(CurrencyConverter.class))
    @Error(value = TU_P_004, level = SeverityType.WARNING)
    private CurrencyCodeType test;

    @Position(value = 1, converter = @Up2Converter(DummyConverter.class))
    @Error(value = TU_P_006, level = SeverityType.ERROR)
    private Integer other;

}
