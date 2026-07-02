package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;

public class TestRecursive1Segment implements Segment {

    @Position(0)
    private String code;

    @Fragment(1)
    private TestRecursive2Segment recursive;

}
