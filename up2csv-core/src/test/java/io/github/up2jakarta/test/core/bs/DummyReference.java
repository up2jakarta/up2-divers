package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.SegmentType;
import jakarta.persistence.Access;
import jakarta.validation.Valid;

import java.util.LinkedList;
import java.util.List;

import static jakarta.persistence.AccessType.PROPERTY;

/**
 * {@link SegmentType#S61}
 */
@Valid
@Access(PROPERTY)
public final class DummyReference extends Parsable implements BusinessObject<String> {

    @Position(0)
    private String reference;

    private final List<DummyAttribute> attributes = new LinkedList<>();

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setReference(String value) {
        if ("*".equals(value)) {
            throw new AccessException(DummyReference.class, "businessId", "invalid identifier");
        }
        this.reference = value;
    }

    public List<DummyAttribute> getAttributes() {
        return attributes;
    }
}
