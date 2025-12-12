package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

abstract class Access1Super implements Segment {

    @Position(value = 0, defaultValue = "*")
    protected String code;

    public abstract String getCode();

    public abstract void setCode(String code);

}
