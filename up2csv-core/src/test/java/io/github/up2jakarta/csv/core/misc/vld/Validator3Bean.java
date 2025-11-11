package io.github.up2jakarta.csv.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.core.misc.ParsedEntity;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@ValidOverride
@SuppressWarnings("unused")
public class Validator3Bean extends ParsedEntity<Long> {

    @Position(0)
    @Up2Decimal(2)
    @NotNull
    protected BigDecimal amount;
    private Long key;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public Long getKey() {
        return key;
    }

    @Override
    public void setKey(Long key) {
        this.key = key;
    }
}
