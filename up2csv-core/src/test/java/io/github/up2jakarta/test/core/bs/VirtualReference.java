package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.fmt.sln.VirtualItemLinker;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static io.github.up2jakarta.test.impl.TermType.NONE;
import static io.github.up2jakarta.test.impl.TermType.UUID;

@Valid
@BusinessType(NONE)
@Error(value = "CSV-C71", level = FATAL)
public abstract sealed class VirtualReference<I extends VirtualItem> implements Segment permits Virtual1Reference, Virtual2Reference {

    @NotBlank
    @Position(0)
    @BusinessType(UUID)
    private final @BusinessId String reference;

    @BusinessLink(value = "73", automatic = true, bean = @Linker(VirtualItemLinker.class))
    private final List<I> items = new LinkedList<>();

    public VirtualReference(String reference) {
        this.reference = reference;
    }

    public final String getReference() {
        return reference;
    }

    public final List<I> getItems() {
        return items;
    }

}
