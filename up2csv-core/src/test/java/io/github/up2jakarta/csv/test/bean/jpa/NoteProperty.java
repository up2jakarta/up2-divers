package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Required;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.CodeList;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
@SuppressWarnings("unused")
public class NoteProperty<C extends CodeList<C>> implements Segment {

    @Position(0)
    @Required
    private String value;

    @Position(1)
    @NotNull
    private C code;

    public NoteProperty(String value, C code) {
        this.value = value;
        this.code = code;
    }

    public NoteProperty() {
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

}
