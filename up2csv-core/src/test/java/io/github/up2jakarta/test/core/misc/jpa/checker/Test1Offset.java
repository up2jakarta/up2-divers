package io.github.up2jakarta.test.core.misc.jpa.checker;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;

@Up2EnableJPA
public class Test1Offset implements Segment {

    @Position(0)
    public final String key;

    @Position(0)
    public final String value;

    public Test1Offset(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
