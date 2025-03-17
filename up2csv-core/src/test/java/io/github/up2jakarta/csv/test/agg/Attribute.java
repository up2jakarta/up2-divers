package io.github.up2jakarta.csv.test.agg;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Number;
import io.github.up2jakarta.csv.extension.Linked;
import io.github.up2jakarta.csv.impl.Parsable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Valid
@SuppressWarnings("unused")
public class Attribute extends Parsable implements Linked {

    @Position(0)
    @Up2Number
    @NotNull
    private Long id;

    @Position(0)
    @NotNull
    private String key;

    @Position(1)
    @NotNull
    private String value;

    public String getKey() {
        return key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean isParent(Object parent) {
        return id != null && (parent instanceof Item i) && id.equals(i.getId());
    }

}
