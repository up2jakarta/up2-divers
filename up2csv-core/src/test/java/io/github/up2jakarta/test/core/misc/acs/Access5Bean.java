package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Boolean;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Access5Bean extends Access5Super<String> {

    @Position(1)
    @Up2Boolean
    @Access(AccessType.FIELD)
    protected Boolean flag;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}

abstract class Access5Super<T> implements Segment {

    @Position(0)
    protected T code;

    public T getCode() {
        throw new RuntimeException("generic");
    }

    public void setCode(T code) {
        throw new RuntimeException("generic");
    }

}
