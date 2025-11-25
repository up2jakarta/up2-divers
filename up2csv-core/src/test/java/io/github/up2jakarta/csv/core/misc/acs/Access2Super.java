package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

abstract class Access2Super implements Segment {

    @Position(value = 0, defaultValue = "*")
    protected String code;

    public String getCode() {
        throw new RuntimeException();
    }

    public void setCode(String code) {
        throw new RuntimeException();
    }

}
