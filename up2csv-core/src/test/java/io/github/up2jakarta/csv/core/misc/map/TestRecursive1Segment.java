package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("ALL")
public class TestRecursive1Segment implements Segment {

    @Position(0)
    private String id;

    @Fragment(1)
    private TestRecursive2Segment recursive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestRecursive2Segment getRecursive() {
        return recursive;
    }

    public void setRecursive(TestRecursive2Segment recursive) {
        this.recursive = recursive;
    }

}
