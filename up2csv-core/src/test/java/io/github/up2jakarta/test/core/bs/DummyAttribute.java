package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.BusinessId;
import io.github.up2jakarta.csv.data.ParentId;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.InputType;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import static jakarta.persistence.AccessType.PROPERTY;

@Valid
@Access(PROPERTY)
@InputType(GroupType.D005)
@SuppressWarnings("unused")
public class DummyAttribute extends Parsable {

    @Position(0)
    @ParentId
    private String parentId;

    @Position(1)
    @BusinessId
    private String businessId;

    @Position(2)
    @NotBlank
    private String value;

    public String getParentId() throws BeanException {
        if ("*".equals(parentId)) {
            throw new BeanException(DummyAttribute.class, "parentId", "invalid identifier");
        }
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBusinessId() throws BeanException {
        if ("*".equals(businessId)) {
            throw new BeanException(DummyAttribute.class, "businessId", "invalid identifier");
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
