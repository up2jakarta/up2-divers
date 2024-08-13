package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.core.Segment;

@SuppressWarnings("ALL")
public class Test2Segment implements Segment {

    @Fragment(-1)
    private TestFragment p;

    public static class TestFragment implements Segment {
    }
}
