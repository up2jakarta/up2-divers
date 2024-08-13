package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Processor;
import io.github.up2jakarta.csv.annotation.Truncated;
import io.github.up2jakarta.csv.core.Segment;
import io.github.up2jakarta.csv.test.DummyTransformer;

@Truncated(1)
@SuppressWarnings("ALL")
public class Test2Processor implements Segment {

    @Position(0)
    @Processor(value = DummyTransformer.class, arguments = "Wrapper")
    private String test;

    public void setTest(String test) {
        this.test = test;
    }

}
