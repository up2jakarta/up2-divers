package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Valid
@SuppressWarnings("unused")
public class Validator15Bean implements Segment {

    @Position(0)
    private String currency;

    @NotNull(payload = Warning.class)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
