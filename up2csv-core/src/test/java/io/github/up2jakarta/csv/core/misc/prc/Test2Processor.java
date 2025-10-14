package io.github.up2jakarta.csv.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.misc.ext.Dummy2;
import io.github.up2jakarta.csv.data.Segment;

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
