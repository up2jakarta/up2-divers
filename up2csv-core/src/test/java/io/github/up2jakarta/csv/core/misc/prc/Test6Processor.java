package io.github.up2jakarta.csv.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.misc.ext.Dummy1;
import io.github.up2jakarta.csv.data.Segment;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_PROCESSOR;

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
