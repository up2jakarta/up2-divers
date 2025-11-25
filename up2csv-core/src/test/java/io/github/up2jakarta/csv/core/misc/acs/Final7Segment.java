package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.acs.Final1Segment.Source;
import io.github.up2jakarta.csv.data.Segment;

public record Final7Segment(@Position(0) String code, Source source) implements Segment {
    @Creator
    private Final7Segment(String code) {
        this(code, Source.CSV);
    }
}
