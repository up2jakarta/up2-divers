package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("ALL")
public class Test6Segment implements Segment {

    @Position(0)
    private final String finalField = "dummy";

    public void setFinalField(String finalField) {
        //this.finalField = finalField;
    }
}
