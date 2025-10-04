package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.ext.Dummy4;

@SuppressWarnings("unused")
public class Test5Processor implements Segment {

    @Position(0)
    @Dummy4
    String attribute;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}