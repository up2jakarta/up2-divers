package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.ext.Dummy1;

@Truncated(1)
@SuppressWarnings("unused")
public class Test1Processor implements Segment {

    @Position(0)
    @Dummy1
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
