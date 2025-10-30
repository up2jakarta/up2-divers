package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Boolean;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class Access5Bean extends Super5Bean<String> {

    @Position(1)
    @Up2Boolean
    protected Boolean flag;

    protected Boolean getFlag() {
        return flag;
    }

    protected void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

abstract class Super5Bean<T> implements Segment {

    @Position(0)
    protected T code;

    public T getCode() {
        throw new RuntimeException("generic");
    }

    public void setCode(T code) {
        throw new RuntimeException("generic");
    }

}
