package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeListAdapter;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter extends CodeListAdapter<CountryCodeType> {

    public static final String ISO_3166 = "ISO-3166";

    public CountryConverter() {
        super(CountryCodeType.class, ISO_3166);
    }

}
