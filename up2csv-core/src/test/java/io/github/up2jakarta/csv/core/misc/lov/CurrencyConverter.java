package io.github.up2jakarta.csv.core.misc.lov;

import io.github.up2jakarta.lov.CodeListAdapter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter extends CodeListAdapter<CurrencyCodeType> {

    public static final String ISO_4217 = "ISO-4217";

    CurrencyConverter() {
        super(CurrencyCodeType.class, ISO_4217);
    }

}
