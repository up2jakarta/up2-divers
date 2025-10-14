package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;

@FragmentOverride(path = {"recursive"})
public class TestRecursive7OverrideSegment extends TestRecursive1Segment {

    @Position(1)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
