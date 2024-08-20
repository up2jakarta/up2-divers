package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;

@SuppressWarnings("ALL")
public class TestRecursive3Segment extends TestRecursive1Segment {

    @Position(10)
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
