package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.ext.Dummy1;

import static io.github.up2jakarta.csv.core.Errors.ERROR_PROCESSOR;

@Truncated(1)
@SuppressWarnings("unused")
public class Test6Processor implements Segment {

    @Position(0)
    @Dummy1
    @Error(value = ERROR_PROCESSOR)
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
