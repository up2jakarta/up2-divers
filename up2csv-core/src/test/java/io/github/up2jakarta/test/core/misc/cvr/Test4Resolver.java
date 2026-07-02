package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;

public class Test4Resolver implements Segment {

    @Position(0)
    @Up2CodeList
    public Number test;

}
