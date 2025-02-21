package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.CodeList;
import io.github.up2jakarta.csv.extension.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
@SuppressWarnings("unused")
public class NoteId<C extends CodeList<C>> implements Nullable, Serializable {

    @Position(0)
    private String value;

    @Position(1)
    @NotNull
    private C schemeId;

    public NoteId(String value, C schemeId) {
        this.value = value;
        this.schemeId = schemeId;
    }

    public NoteId() {
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

    @Override
    public boolean isNotNull() {
        return value != null;
    }

}
