package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import static io.github.up2jakarta.test.impl.TermType.A002;
import static io.github.up2jakarta.test.impl.TermType.D009;
import static jakarta.persistence.AccessType.PROPERTY;

@Valid
@Access(PROPERTY)
@BusinessType(D009)
@SuppressWarnings("unused")
public class DummyAttribute extends Parsable {

    @NotEmpty
    @Position(0)
    @ReferenceId("71")
    private String parentId;

    @NotBlank
    @Position(1)
    @BusinessId
    private String businessId;

    @Position(2)
    @NotBlank
    @BusinessType(A002)
    private String value;

    public String getParentId() {
        if ("*".equals(parentId)) {
            throw new AccessException(DummyAttribute.class, "parentId", "invalid identifier");
        }
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBusinessId() {
        if ("*".equals(businessId)) {
            throw new AccessException(DummyAttribute.class, "businessId", "invalid identifier");
        }
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
