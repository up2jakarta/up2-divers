package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import static io.github.up2jakarta.test.impl.TermType.I006;

@Valid
public final class Virtual2Item extends VirtualItem {

    @NotNull
    @Fragment(0)
    private final @Valid VirtualId key;

    public Virtual2Item(VirtualId key) {
        this.key = key;
    }

    public VirtualId getKey() {
        return key;
    }

    @Override
    public Integer getId() {
        return key.getId();
    }

    @ValidOverride(groups = Segment.class)
    public static class VirtualId implements Segment {

        @Up2Number
        @Position(0)
        @BusinessType(I006)
        @NotNull(groups = Segment.class)
        private final @BusinessId Integer id;

        public VirtualId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

    }
}
