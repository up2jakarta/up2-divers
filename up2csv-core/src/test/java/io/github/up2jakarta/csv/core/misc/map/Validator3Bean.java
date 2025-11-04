package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@ValidOverride
@SuppressWarnings("unused")
public class Validator3Bean implements Segment {

    @Position(0)
    @Up2Decimal(2)
    @NotNull
    protected BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
