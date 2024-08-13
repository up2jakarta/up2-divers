package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Processor;
import io.github.up2jakarta.csv.annotation.Truncated;
import io.github.up2jakarta.csv.core.Segment;
import io.github.up2jakarta.csv.test.DummyTransformer;

@Truncated(1)
@SuppressWarnings("ALL")
public class Test1Processor implements Segment {

    @Position(0)
    @Processor(DummyTransformer.class)
    private String test;

    public void setTest(String test) {
        this.test = test;
    }

}
