package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.fmt.sln.VirtualAttributeLinker;
import io.github.up2jakarta.test.impl.BusinessType;

import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.D004;

@BusinessType(D004)
@ValidOverride(groups = Segment.class)
@Error(value = "CSV-C73", level = WARNING)
public abstract sealed class VirtualItem extends Parsable permits Virtual1Item, Virtual2Item {

    @BusinessLink(value = "72", automatic = true, bean = @Linker(VirtualAttributeLinker.class))
    private final List<VirtualAttribute> attributes = new LinkedList<>();

    public final List<VirtualAttribute> getAttributes() {
        return attributes;
    }

    public abstract Integer getId();
}
