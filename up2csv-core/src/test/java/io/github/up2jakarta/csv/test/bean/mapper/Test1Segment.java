package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.extension.Segment;

@SuppressWarnings("ALL")
public class Test1Segment implements Segment {

    @Fragment(0)
    private TestFragment p;

    public static class TestFragment {
    }
}
