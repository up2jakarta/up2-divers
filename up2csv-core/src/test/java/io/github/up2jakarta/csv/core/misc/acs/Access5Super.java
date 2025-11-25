package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

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
