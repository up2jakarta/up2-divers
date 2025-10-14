package io.github.up2jakarta.csv.core.misc.jpa;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Enumerated;

//@Entity
@Up2EnableJPA
@SuppressWarnings("unused")
public class Test3Bean implements Segment {

    @Position(0)
    @Enumerated
    private XML1Enum enum1;

    public XML1Enum getEnum1() {
        return enum1;
    }

    public void setEnum1(XML1Enum enum1) {
        this.enum1 = enum1;
    }
}
