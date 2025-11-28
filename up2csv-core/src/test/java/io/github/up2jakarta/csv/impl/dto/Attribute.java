package io.github.up2jakarta.csv.impl.dto;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.data.ParentId;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static io.github.up2jakarta.lov.SeverityType.WARNING;

@Valid
@Error(value = "CSV-C05", level = WARNING)
@InputType(GroupType.D005)
@SuppressWarnings("unused")
public class Attribute extends Parsable {

    @Position(0)
    @Up2Number
    @NotNull
    @ParentId
    private Long itemId;

    @Position(1)
    @NotEmpty
    private String key;

    @Position(value = 2, required = true)
    @NotEmpty
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
