package io.github.up2jakarta.test.core.misc.prc;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.ext.Dummy1Processor;
import io.github.up2jakarta.test.core.misc.ext.Dummy3;

import static io.github.up2jakarta.csv.api.IEvent.EC_PROCESSOR;

public class Test3Processor implements Segment {

    @Position(0)
    @Dummy3
    @Error(value = Dummy1Processor.TU_P_001, level = SeverityType.WARNING)
    private String test;

    @Position(1)
    @Dummy3
    @Error(value = EC_PROCESSOR, level = SeverityType.WARNING)
    private String other;

}
