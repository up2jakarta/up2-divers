package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;

public class Final9Segment implements Segment {

    @Position(0)
    @Up2Number
    public final Integer key;

    @Position(1)
    public String value;

    @Creator
    public Final9Segment(Integer key) {
        this.key = key;
    }

}
