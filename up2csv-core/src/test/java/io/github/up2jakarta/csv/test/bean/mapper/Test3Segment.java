package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.core.Segment;

@SuppressWarnings("ALL")
public class Test3Segment implements Segment {

    @Position(-1)
    private String p;

    public void setP(String p) {
        this.p = p;
    }
}
