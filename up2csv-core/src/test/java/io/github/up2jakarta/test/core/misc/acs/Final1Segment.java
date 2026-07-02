package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.test.core.misc.map.Test6Segment;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
@SuppressWarnings("unused")
public class Final1Segment extends Test6Segment {

    private final Source source;

    @Creator
    private Final1Segment(String code) {
        super(code);
        this.source = Source.CSV;
    }

    public Final1Segment(String code, Source source) {
        super(code);
        if (source == Source.CSV) {
            throw new IllegalArgumentException("trying to hack the source");
        }
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

    public enum Source {
        CSV, WEB, KPI
    }

}
