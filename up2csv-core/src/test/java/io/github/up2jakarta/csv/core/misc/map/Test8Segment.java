package io.github.up2jakarta.csv.core.misc.map;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@Access(AccessType.FIELD)
public class Test8Segment extends Test6Segment {

    public Test8Segment(String code) {
        super(code);
    }
}
