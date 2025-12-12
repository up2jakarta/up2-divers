package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;

public final class ExtendedCountryBean extends CountryBean {

    @Position(0)
    private String currency;

    public String getCurrency() {
        return currency;
    }

}
