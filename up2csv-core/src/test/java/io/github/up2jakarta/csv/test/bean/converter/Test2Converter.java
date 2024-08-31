package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Converter;
import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.ext.DummyConverter;

@SuppressWarnings("unused")
public class Test2Converter implements Segment {

    public static final String TU_P_004 = "TU-P004";
    public static final String TU_P_006 = "TU-P006";

    @Position(0)
    @Converter(CurrencyConverter.class)
    @Error(value = TU_P_004, severity = SeverityType.WARNING)
    private CurrencyCodeType test;

    @Position(1)
    @Converter(DummyConverter.class)
    @Error(value = TU_P_006, severity = SeverityType.FATAL)
    private Integer other;

    public void setTest(CurrencyCodeType test) {
        this.test = test;
    }

    public void setOther(Integer other) {
        this.other = other;
    }
}
