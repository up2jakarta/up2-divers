package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.fmt.sln.DummyAttributeLinker;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.NONE;
import static io.github.up2jakarta.test.impl.TermType.UUID;
import static jakarta.persistence.AccessType.PROPERTY;

@Valid
@Access(PROPERTY)
@BusinessType(NONE)
@BusinessObject("71")
@Error(value = "CSV-C71", level = FATAL)
public class DummyReference extends Parsable {

    @Position(0)
    @BusinessId
    @NotBlank
    @BusinessType(UUID)
    private String reference;

    @BusinessLink(value = "72", bean = @Linker(DummyAttributeLinker.class))
    @Error(value = "CSV-C72", level = WARNING)
    private final List<DummyAttribute> attributes = new LinkedList<>();

    public String getReference() {
        return reference;
    }

    public void setReference(String value) {
        if ("$".equals(value)) {
            throw new AccessException(DummyReference.class, "reference", "invalid identifier");
        }
        this.reference = value;
        if ("*".equals(value)) {
            throw new AccessException(DummyReference.class, "reference", "invalid identifier");
        }
    }

    public List<DummyAttribute> getAttributes() {
        return attributes;
    }
}
