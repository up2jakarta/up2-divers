package io.github.up2jakarta.csv.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.ext.Dummy3;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;

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
    @Error(value = TU_P_003, severity = SeverityType.ERROR)
    private String other;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
