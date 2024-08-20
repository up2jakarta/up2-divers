package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.misc.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter extends CodeListConverter<CurrencyCodeType> {

    public static final String ISO_4217 = "ISO-4217";

    CurrencyConverter() {
        super(CurrencyCodeType.class, ISO_4217);
    }

}
