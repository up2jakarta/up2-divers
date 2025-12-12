package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;

@FragmentOverride(path = {"recursive", "recursive"})
public class TestRecursive7Override extends TestRecursive1Segment {

    @Position(2)
    private String name;

}
