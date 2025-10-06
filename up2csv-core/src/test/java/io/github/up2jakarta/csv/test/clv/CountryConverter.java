package io.github.up2jakarta.csv.test.clv;

import io.github.up2jakarta.xml.clv.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter extends CodeListConverter<CountryCodeType> {

    public static final String ISO_3166 = "ISO-3166";

    CountryConverter() {
        super(CountryCodeType.class, ISO_3166);
    }

}
