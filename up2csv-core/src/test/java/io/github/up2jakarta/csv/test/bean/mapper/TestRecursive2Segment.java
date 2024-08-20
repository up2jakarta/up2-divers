package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;

@SuppressWarnings("ALL")
public class TestRecursive2Segment implements Segment {

    @Position(0)
    private String id;

    @Fragment(1)
    private TestRecursive1Segment recursive;

    public void setId(String id) {
        this.id = id;
    }

    public void setRecursive(TestRecursive1Segment recursive) {
        this.recursive = recursive;
    }
}
