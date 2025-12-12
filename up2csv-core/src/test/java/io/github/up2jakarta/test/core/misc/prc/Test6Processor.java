package io.github.up2jakarta.test.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.ext.Dummy1;

import static io.github.up2jakarta.csv.api.IEvent.EC_PROCESSOR;

@Truncated(1)
public class Test6Processor implements Segment {

    @Position(0)
    @Dummy1
    @Error(value = EC_PROCESSOR)
    private String test;

}
