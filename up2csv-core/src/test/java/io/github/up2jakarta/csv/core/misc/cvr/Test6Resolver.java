package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.core.misc.ext.Up2Dummy;
import io.github.up2jakarta.csv.data.Segment;

public class Test6Resolver implements Segment {

    @Position(0)
    @Up2Number
    @Up2Dummy
    public Integer test;

}
