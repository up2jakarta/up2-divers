package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.misc.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter extends CodeListConverter<CountryCodeType> {

    public static final String ISO_3166 = "ISO-3166";

    CountryConverter() {
        super(CountryCodeType.class, ISO_3166);
    }

}
