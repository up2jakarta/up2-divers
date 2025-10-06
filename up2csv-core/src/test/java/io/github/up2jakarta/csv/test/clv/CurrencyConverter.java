package io.github.up2jakarta.csv.test.clv;

import io.github.up2jakarta.xml.clv.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter extends CodeListConverter<CurrencyCodeType> {

    public static final String ISO_4217 = "ISO-4217";

    CurrencyConverter() {
        super(CurrencyCodeType.class, ISO_4217);
    }

}
