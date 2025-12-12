package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

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
