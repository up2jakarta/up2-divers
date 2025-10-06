package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.clv.CurrencyConverter;
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
