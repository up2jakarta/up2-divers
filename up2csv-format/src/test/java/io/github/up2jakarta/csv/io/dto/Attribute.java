package io.github.up2jakarta.csv.io.dto;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.ParentId;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Valid
@SuppressWarnings("unused")
public class Attribute implements Segment {

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
