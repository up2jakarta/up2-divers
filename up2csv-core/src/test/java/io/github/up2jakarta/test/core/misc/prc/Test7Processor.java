package io.github.up2jakarta.test.core.misc.prc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.cfg.Up2Trim;

public class Test7Processor {

    @Up2Token
    @Up2Trim("undefined")
    @Position(value = 0, defaultValue = "default")
    String value;

    @Up2Number
    @Up2Trim("undefined")
    @Position(value = 0, defaultValue = "99")
    Integer number;

}
