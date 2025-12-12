package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public final class SimpleSegment implements Segment {

    @Position(0)
    private String code;
    @Position(1)
    private String name;
    @Position(2)
    private String role;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

}
