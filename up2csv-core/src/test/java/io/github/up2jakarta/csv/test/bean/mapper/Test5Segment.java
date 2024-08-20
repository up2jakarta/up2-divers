package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;

@SuppressWarnings("ALL")
public class Test5Segment implements Segment {

    @Position(0)
    public String publicField;

    public void setPublicField(String publicField) {
        this.publicField = publicField;
    }
}
