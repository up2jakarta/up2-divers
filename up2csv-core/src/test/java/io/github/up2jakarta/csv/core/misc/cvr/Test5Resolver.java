package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

public class Test5Resolver implements Segment {

    @Position(0)
    @Up2Number
    @Up2Decimal(0)
    public Integer test;

}
