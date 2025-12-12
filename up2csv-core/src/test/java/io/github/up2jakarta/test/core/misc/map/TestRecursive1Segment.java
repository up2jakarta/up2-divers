package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public class TestRecursive1Segment implements Segment {

    @Position(0)
    private String code;

    @Fragment(1)
    private TestRecursive2Segment recursive;

}
