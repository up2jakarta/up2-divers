package io.github.up2jakarta.test.core.misc.jpa;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.lov.CodeList;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class NoteProperty<C extends CodeList<C>> implements Segment {

    @Position(value = 0, required = true)
    private String value;

    @Position(1)
    @NotNull
    private C code;

    public String getValue() {
        return value;
    }

    public C getCode() {
        return code;
    }

}
