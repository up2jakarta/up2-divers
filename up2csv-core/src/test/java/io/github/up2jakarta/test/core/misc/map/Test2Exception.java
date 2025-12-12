package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.ext.Dummy1;

public class Test2Exception implements Segment {

    @Position(0)
    @Error(value = "W001", level = SeverityType.WARNING)
    @Dummy1
    private String id1;

    @Position(1)
    @Error(value = "E002", level = SeverityType.ERROR)
    @Dummy1
    private String id2;

    @Position(2)
    @Dummy1
    @Error(value = "F003", level = SeverityType.FATAL)
    private String dummy;

}
