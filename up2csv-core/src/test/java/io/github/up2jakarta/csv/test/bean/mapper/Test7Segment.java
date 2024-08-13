package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.core.Segment;

@SuppressWarnings("ALL")
public class Test7Segment implements Segment {

    @Position(0)
    private static String staticField;

    public static void setStaticField(String staticField) {
        Test7Segment.staticField = staticField;
    }
}
