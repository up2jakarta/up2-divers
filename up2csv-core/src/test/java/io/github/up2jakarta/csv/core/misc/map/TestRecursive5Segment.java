package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;

@SuppressWarnings("ALL")
public class TestRecursive5Segment extends TestRecursive4Segment {

    @Position(1)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
