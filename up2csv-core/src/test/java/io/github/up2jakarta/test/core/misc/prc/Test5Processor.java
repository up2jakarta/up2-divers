package io.github.up2jakarta.test.core.misc.prc;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.ext.Dummy4;

public class Test5Processor implements Segment {

    @Position(0)
    @Dummy4
    String attribute;

}