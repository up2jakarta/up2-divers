package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Access1Bean extends Access1Super {

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}

abstract class Access1Super implements Segment {

    @Position(value = 0, defaultValue = "*")
    protected String code;

    public abstract String getCode();

    public abstract void setCode(String code);

}
