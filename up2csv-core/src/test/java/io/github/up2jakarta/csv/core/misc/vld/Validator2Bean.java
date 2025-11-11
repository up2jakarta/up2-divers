package io.github.up2jakarta.csv.core.misc.vld;

import io.github.up2jakarta.csv.api.Fatal;
import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@ValidOverride
@SuppressWarnings("unused")
public class Validator2Bean implements Segment {

    @Position(0)
    @Up2Token
    @Size(min = 1, max = 3, payload = Warning.class)
    @NotEmpty(payload = Fatal.class)
    protected String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
