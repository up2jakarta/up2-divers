package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;

public class TestRecursive6Segment extends TestRecursive4Segment {

    @Position(1)
    private String any;

    @Fragment(2)
    private TestRecursive5Segment fragment;

}
