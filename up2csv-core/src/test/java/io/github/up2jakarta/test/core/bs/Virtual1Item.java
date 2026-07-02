package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.constraints.NotNull;

import static io.github.up2jakarta.test.impl.TermType.I006;

public final class Virtual1Item extends VirtualItem {

    @Up2Number
    @Position(0)
    @BusinessType(I006)
    @NotNull(groups = Segment.class)
    private final @BusinessId Integer id;

    public Virtual1Item(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

}
