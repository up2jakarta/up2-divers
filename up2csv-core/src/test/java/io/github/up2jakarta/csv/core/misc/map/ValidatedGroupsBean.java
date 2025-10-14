package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.api.Fatal;
import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.core.misc.vld.Up2Group;
import io.github.up2jakarta.csv.data.Segment;
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
