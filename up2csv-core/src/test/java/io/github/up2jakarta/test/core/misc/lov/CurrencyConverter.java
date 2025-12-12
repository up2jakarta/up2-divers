package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeListAdapter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter extends CodeListAdapter<CurrencyCodeType> {

    public static final String ISO_4217 = "ISO-4217";

    public CurrencyConverter() {
        super(CurrencyCodeType.class, ISO_4217);
    }

}
