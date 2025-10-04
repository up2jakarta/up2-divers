package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("ALL")
public class TestRecursive2Segment implements Segment {

    @Position(0)
    private String id;

    @Fragment(1)
    private TestRecursive1Segment recursive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestRecursive1Segment getRecursive() {
        return recursive;
    }

    public void setRecursive(TestRecursive1Segment recursive) {
        this.recursive = recursive;
    }
}
