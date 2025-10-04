package io.github.up2jakarta.csv.test.sample;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Required;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.ParentId;
import io.github.up2jakarta.csv.impl.Parsable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Valid
@SuppressWarnings("unused")
public class Attribute extends Parsable {

    @Position(0)
    @Up2Number
    @NotNull
    @ParentId(Item.class)
    private Long itemId;

    @Position(1)
    @NotEmpty
    private String key;

    @Position(2)
    @NotEmpty
    @Required
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
