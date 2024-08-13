package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;

@SuppressWarnings("unused")
public final class ExtendedCountryBean extends CountryBean {

    @Position(0)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
