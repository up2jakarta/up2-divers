package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;

public interface FinalSegment extends Segment {

    Integer getKey();

    String getCode();

    String getValue();
}
