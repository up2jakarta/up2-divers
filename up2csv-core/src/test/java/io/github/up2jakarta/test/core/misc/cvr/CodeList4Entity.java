package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.lov.Test4CodeList;

public class CodeList4Entity implements Segment {

    @Position(0)
    @Up2CodeList("TestType")
    private Test4CodeList invalid;

}
