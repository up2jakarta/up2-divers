package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;

public class Final8Segment implements Segment {

    @Position(0)
    @Up2Number
    public final Integer key;

    @Position(1)
    public final String value;

    @Creator
    public Final8Segment(String value, Integer key) {
        this.key = key;
        this.value = value;
    }

    @Creator
    public Final8Segment(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

}
