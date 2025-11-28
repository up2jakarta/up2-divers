package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.core.misc.lov.Test4CodeList;
import io.github.up2jakarta.csv.data.Segment;

public class CodeList4Entity implements Segment {

    @Position(0)
    @Up2CodeList("TestType")
    private Test4CodeList invalid;

}
