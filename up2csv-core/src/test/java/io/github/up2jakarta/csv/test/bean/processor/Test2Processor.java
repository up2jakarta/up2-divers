package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Truncated;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.ext.Dummy2;

@Truncated(1)
public class Test2Processor implements Segment {

    @Position(0)
    @Dummy2
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
