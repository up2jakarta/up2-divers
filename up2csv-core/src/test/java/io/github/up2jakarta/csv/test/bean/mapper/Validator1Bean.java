package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Valid
@SuppressWarnings("unused")
public class Validator1Bean implements Segment {

    @Position(0)
    @Error(CurrencyConverter.ISO_4217)
    @Size(max = 3)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
