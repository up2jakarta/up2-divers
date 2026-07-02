package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;

public class Test5Resolver implements Segment {

    @Position(0)
    @Up2Number
    @Up2Decimal(0)
    public Integer test;

}
