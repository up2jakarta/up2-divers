package io.github.up2jakarta.csv.core.misc.lov;

import io.github.up2jakarta.lov.CodeListAdapter;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter extends CodeListAdapter<CountryCodeType> {

    public static final String ISO_3166 = "ISO-3166";

    CountryConverter() {
        super(CountryCodeType.class, ISO_3166);
    }

}
