package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.lov.EnumLike;

public class Test3Resolver implements Segment {

    @Position(0)
    @Up2CodeList("UnitType")
    private EnumLike unit = EnumLike.NAN;

    public EnumLike getUnit() {
        return unit;
    }

}
