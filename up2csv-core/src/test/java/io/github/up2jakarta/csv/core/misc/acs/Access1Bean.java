package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public class Access1Bean extends Super1Bean {

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}

abstract class Super1Bean implements Segment {

    @Position(value = 0, defaultValue = "*")
    protected String code;

    public abstract String getCode();

    public abstract void setCode(String code);

}
