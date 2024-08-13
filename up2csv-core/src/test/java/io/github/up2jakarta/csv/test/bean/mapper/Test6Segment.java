package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.core.Segment;

@SuppressWarnings("ALL")
public class Test6Segment implements Segment {

    @Position(0)
    private final String finalField = "dummy";

    public void setFinalField(String finalField) {
        //this.finalField = finalField;
    }
}
