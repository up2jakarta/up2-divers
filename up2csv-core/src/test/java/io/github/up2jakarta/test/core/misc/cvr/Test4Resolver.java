package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.data.Segment;

public class Test4Resolver implements Segment {

    @Position(0)
    @Up2CodeList
    public Number test;

}
