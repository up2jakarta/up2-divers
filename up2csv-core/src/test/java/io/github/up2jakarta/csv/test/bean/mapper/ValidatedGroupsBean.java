package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Token;
import io.github.up2jakarta.csv.annotation.Validated;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.csv.test.validation.Up2Group;
import jakarta.validation.constraints.Size;

@Validated(groups = Up2Group.class)
@SuppressWarnings("unused")
public class ValidatedGroupsBean implements Segment {

    @Position(0)
    @Up2Token
    @Size(min = 1, max = 3, payload = Errors.Fatal.class, groups = Up2Group.class)
    @Size(min = 1, max = 2, payload = Errors.Error.class)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
