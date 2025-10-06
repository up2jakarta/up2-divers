package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.api.Fatal;
import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.valid.Up2Group;
import jakarta.validation.constraints.Size;

@ValidOverride(groups = Up2Group.class)
@SuppressWarnings("unused")
public class ValidatedGroupsBean implements Segment {

    @Position(0)
    @Up2Token
    @Size(min = 1, max = 3, payload = Warning.class, groups = Up2Group.class)
    @Size(min = 1, max = 2, payload = Fatal.class)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
