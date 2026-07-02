package io.github.up2jakarta.test.impl.dto;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@Error(value = "CSV-C09", level = WARNING)
@BusinessType(D009)
public class Attribute extends Parsable {

    @Position(0)
    @BusinessId
    @BusinessType(A001)
    private final @NotEmpty String key;

    @Position(value = 1, required = true)
    @BusinessType(A002)
    private final @NotEmpty String value;

    public Attribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
