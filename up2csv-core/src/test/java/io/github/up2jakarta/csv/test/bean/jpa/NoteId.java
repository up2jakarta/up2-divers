package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Nullable;
import io.github.up2jakarta.xml.codelist.CodeList;
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
    private C code;

    public NoteId(String value, C code) {
        this.value = value;
        this.code = code;
    }

    public NoteId() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public C getCode() {
        return code;
    }

    public void setCode(C code) {
        this.code = code;
    }

    @Override
    public boolean isNotNull() {
        return value != null;
    }

}
