package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.misc.Errors;
import jakarta.validation.constraints.Size;

@ValidOverride
@SuppressWarnings("unused")
public class Validator2Bean implements Segment {

    @Position(0)
    @Up2Token
    @Size(min = 1, max = 3, payload = Errors.Warning.class)
    @Size(min = 1, max = 3, payload = Errors.Warning.class)
    protected String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
