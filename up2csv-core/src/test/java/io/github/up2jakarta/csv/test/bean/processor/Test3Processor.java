package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.csv.test.ext.Dummy1Processor;
import io.github.up2jakarta.csv.test.ext.Dummy3;
import io.github.up2jakarta.xml.api.SeverityType;

@SuppressWarnings("unused")
public class Test3Processor implements Segment {

    @Position(0)
    @Dummy3
    @Error(value = Dummy1Processor.TU_P_001, severity = SeverityType.WARNING)
    private String test;

    @Position(1)
    @Dummy3
    @Error(value = Errors.ERROR_PROCESSOR, severity = SeverityType.WARNING)
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
