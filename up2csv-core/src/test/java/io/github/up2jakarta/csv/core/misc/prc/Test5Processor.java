package io.github.up2jakarta.csv.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.ext.Dummy4;
import io.github.up2jakarta.csv.data.Segment;

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