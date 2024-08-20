package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.test.extension.Dummy3;

@SuppressWarnings("unused")
public class Test4Processor implements Segment {

    public static final String TU_P_002 = "TU-P002";
    public static final String TU_P_003 = "TU-P003";

    @Position(0)
    @Dummy3
    @Error(value = TU_P_002, severity = SeverityType.WARNING)
    private String test;

    @Position(1)
    @Dummy3
    @Error(value = TU_P_003, severity = SeverityType.FATAL)
    private String other;

    public void setTest(String test) {
        this.test = test;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
