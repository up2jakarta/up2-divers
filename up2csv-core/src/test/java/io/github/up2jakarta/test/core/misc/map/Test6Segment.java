package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Test6Segment implements Segment {

    @Position(0)
    public final String code;

    public Test6Segment(String code) {
        this.code = code;
    }

    public final String getCode() {
        return code;
    }

}
