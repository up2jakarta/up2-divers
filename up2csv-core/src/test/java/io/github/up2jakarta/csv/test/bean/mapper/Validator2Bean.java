package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Token;
import io.github.up2jakarta.csv.annotation.Validated;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.Errors;
import jakarta.validation.constraints.Size;

@Validated
@SuppressWarnings("unused")
public class Validator2Bean implements Segment {

    @Position(0)
    @Up2Token
    @Size(min = 1, max = 3, payload = Errors.Fatal.class)
    @Size(min = 1, max = 3, payload = Errors.Error.class)
    protected String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
