package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.extension.Dummy4;

@SuppressWarnings("unused")
public class Test5Processor implements Segment {

    @Position(0)
    @Dummy4
    String attribute;

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}