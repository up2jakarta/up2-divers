package io.github.up2jakarta.test.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.ext.Dummy1;

@Truncated(1)
public class Test1Processor implements Segment {

    @Position(0)
    @Dummy1
    private String test;

}
