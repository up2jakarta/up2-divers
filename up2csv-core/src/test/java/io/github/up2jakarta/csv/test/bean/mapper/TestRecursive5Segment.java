package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;

@SuppressWarnings("ALL")
public class TestRecursive5Segment extends TestRecursive4Segment {

    @Position(1)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
