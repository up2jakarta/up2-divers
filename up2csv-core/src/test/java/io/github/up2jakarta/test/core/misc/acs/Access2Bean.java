package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Access2Bean extends Access2Super {

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}

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
