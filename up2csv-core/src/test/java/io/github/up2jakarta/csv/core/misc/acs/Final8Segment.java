package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@Access(AccessType.FIELD)
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
