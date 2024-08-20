package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;

@SuppressWarnings("ALL")
public class TestRecursive6Segment extends TestRecursive4Segment {

    @Position(1)
    private String any;

    @Fragment(2)
    private TestRecursive5Segment fragment;

    public String getAny() {
        return any;
    }

    public void setAny(String any) {
        this.any = any;
    }

    public TestRecursive5Segment getFragment() {
        return fragment;
    }

    public void setFragment(TestRecursive5Segment fragment) {
        this.fragment = fragment;
    }
}
