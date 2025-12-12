package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.data.Segment;

public class Test1Segment implements Segment {

    @Fragment(0)
    private TestFragment p;

    public static class TestFragment {
    }
}
