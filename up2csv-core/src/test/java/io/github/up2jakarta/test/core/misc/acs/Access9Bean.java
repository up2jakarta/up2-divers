package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Access9Bean extends Access9Super<String> {

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = String.valueOf(code);
    }
}

abstract class Access9Super<T> implements Segment {

    @Position(0)
    protected T code;

}
