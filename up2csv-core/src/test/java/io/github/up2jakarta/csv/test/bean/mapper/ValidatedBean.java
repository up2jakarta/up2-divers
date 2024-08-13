package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Token;
import io.github.up2jakarta.csv.annotation.Validated;
import io.github.up2jakarta.csv.core.Segment;
import io.github.up2jakarta.csv.misc.SeverityType;
import jakarta.validation.constraints.Size;

@Validated
@SuppressWarnings("unused")
public class ValidatedBean implements Segment {

    @Position(0)
    @Token
    @Size(min = 1, max = 3, payload = SeverityType.Level.Fatal.class)
    @Size(min = 1, max = 3, payload = SeverityType.Level.Error.class)
    protected String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
