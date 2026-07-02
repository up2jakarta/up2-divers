package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;

public class Access31Bean implements Segment {

    @Position(value = 0, defaultValue = "*")
    private String code;

}
