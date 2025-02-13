package io.github.up2jakarta.csv.entities.invoincing;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.CodeList;
import io.github.up2jakarta.csv.extension.Segment;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
@SuppressWarnings("unused")
public class Identifier<C extends CodeList<C>> implements Segment, Serializable {

    @Position(0)
    private String value;

    @Position(1)
    @NotNull
    private C schemeId;

    public Identifier() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public C getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(C schemeId) {
        this.schemeId = schemeId;
    }

}
