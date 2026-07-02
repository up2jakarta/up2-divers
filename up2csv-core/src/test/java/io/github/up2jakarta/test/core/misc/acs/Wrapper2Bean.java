package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;

public class Wrapper2Bean implements Segment {

    @Fragment(0)
    private WrapperTest<Integer> test;

}
