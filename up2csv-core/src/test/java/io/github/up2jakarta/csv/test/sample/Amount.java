package io.github.up2jakarta.csv.test.sample;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.impl.Parsable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Valid
@SuppressWarnings("unused")
public class Amount extends Parsable {

    @Position(0)
    @Up2Number
    @NotNull
    private Long key;

    @Position(1)
    @Up2Decimal(2)
    @NotNull
    private BigDecimal value;

    @Position(2)
    @NotEmpty
    private String description;

    public Amount() {
    }

    public Amount(Long key, BigDecimal value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum Type {
        NONE, CHARGE, ALLOWANCE
    }

}
