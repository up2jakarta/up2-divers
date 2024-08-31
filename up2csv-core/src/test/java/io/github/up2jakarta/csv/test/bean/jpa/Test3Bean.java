package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.extension.Segment;
import jakarta.persistence.Enumerated;

//@Entity
@Up2EnableJPA
@SuppressWarnings("unused")
public class Test3Bean implements Segment {

    @Position(0)
    @Enumerated
    private XML1Enum enum1;

    public void setEnum1(XML1Enum enum1) {
        this.enum1 = enum1;
    }
}
