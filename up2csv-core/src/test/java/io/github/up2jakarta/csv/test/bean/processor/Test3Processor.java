package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.extension.Dummy3;

@SuppressWarnings("unused")
public class Test3Processor implements Segment {

    @Position(0)
    @Dummy3
    private String test;

    @Position(1)
    @Dummy3
    private String other;

    public void setTest(String test) {
        this.test = test;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
