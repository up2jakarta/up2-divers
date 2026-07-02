package io.github.up2jakarta.test.impl.dto;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@Error("CSV-C06")
@BusinessType(D006)
public class Amount extends Parsable {

    @Position(0)
    @Up2Number
    @BusinessType(A001)
    private final @NotNull Long key;

    @Position(1)
    @Up2Decimal(2)
    @BusinessType(A002)
    private final @NotNull BigDecimal value;

    @Position(2)
    @BusinessType(A003)
    private final @NotEmpty String description;

    public Amount(Long key, BigDecimal value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    public Long getKey() {
        return key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public enum Type {
        NONE, CHARGE, ALLOWANCE
    }

}
