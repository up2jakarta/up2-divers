package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;

public class Generic4Bean implements Segment {

    @Fragment(0)
    private TUGeneric<Integer> test;

}
