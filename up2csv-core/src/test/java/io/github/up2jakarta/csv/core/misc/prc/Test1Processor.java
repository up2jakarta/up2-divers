package io.github.up2jakarta.csv.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.misc.ext.Dummy1;
import io.github.up2jakarta.csv.data.Segment;

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
