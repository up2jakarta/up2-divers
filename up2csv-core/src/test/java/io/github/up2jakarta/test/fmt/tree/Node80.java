package io.github.up2jakarta.test.fmt.tree;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import io.github.up2jakarta.test.impl.TermType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

import static io.github.up2jakarta.lov.SeverityType.WARNING;

@Valid
@BusinessType(TermType.NODE)
@Error(value = "CSV-N0DE", level = WARNING)
@SuppressWarnings("unused")
public abstract class Node80 extends Parsable {

    @Position(0)
    @BusinessId
    @NotEmpty
    @BusinessType(TermType.UUID)
    private String reference;

    @NotEmpty
    @Position(1)
    @Error(value = "CSV-N001", level = WARNING)
    @BusinessType(TermType.NONE)
    private String value;

    public final String getReference() {
        return reference;
    }

    public final String getValue() {
        return value;
    }

    public abstract List<? extends Node80> getNodes();

}
