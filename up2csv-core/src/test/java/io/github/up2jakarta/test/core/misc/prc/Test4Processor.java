package io.github.up2jakarta.test.core.misc.prc;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.ext.Dummy3;

public class Test4Processor implements Segment {

    public static final String TU_P_002 = "TU-P002";
    public static final String TU_P_003 = "TU-P003";

    @Position(0)
    @Dummy3
    @Error(value = TU_P_002, level = SeverityType.WARNING)
    private String test;

    @Position(1)
    @Dummy3
    @Error(value = TU_P_003, level = SeverityType.ERROR)
    private String other;

}
