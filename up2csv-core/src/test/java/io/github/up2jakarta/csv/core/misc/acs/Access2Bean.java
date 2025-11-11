package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public class Access2Bean extends Super2Bean {

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}

abstract class Super2Bean implements Segment {

    @Position(value = 0, defaultValue = "*")
    protected String code;

    public String getCode() {
        throw new RuntimeException();
    }

    public void setCode(String code) {
        throw new RuntimeException();
    }

}
