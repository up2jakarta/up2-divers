package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;

@SuppressWarnings("ALL")
public class Test4Segment implements Segment {

    @Position(0)
    private String Upper;

    public void setUpper(String upper) {
        Upper = upper;
    }

}
