package io.github.up2jakarta.test.core.misc.prc;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.test.core.misc.ext.Dummy2;

@Truncated(1)
public class Test2Processor implements Segment {

    @Position(0)
    @Dummy2
    private String test;

}
