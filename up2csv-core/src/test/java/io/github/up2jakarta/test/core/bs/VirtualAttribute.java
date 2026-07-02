package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@BusinessType(D009)
@SuppressWarnings("unused")
@Error(value = "CSV-C72", level = WARNING)
public class VirtualAttribute extends Parsable {

    @Position(0)
    @NotBlank
    @BusinessType(A001)
    private final String key;

    @Position(1)
    @NotBlank
    @BusinessType(A002)
    private final String value;

    public VirtualAttribute(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
