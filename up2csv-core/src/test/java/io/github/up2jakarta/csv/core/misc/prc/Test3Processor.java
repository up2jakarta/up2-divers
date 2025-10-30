package io.github.up2jakarta.csv.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.ext.Dummy1Processor;
import io.github.up2jakarta.csv.core.misc.ext.Dummy3;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_PROCESSOR;

@SuppressWarnings("unused")
public class Test3Processor implements Segment {

    @Position(0)
    @Dummy3
    @Error(value = Dummy1Processor.TU_P_001, severity = SeverityType.WARNING)
    private String test;

    @Position(1)
    @Dummy3
    @Error(value = ERROR_PROCESSOR, severity = SeverityType.WARNING)
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
