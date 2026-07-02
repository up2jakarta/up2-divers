package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;

public abstract class GenericBean<T> implements Segment {

    @Position(0)
    private String currency;

    @Position(1)
    private T code;

    public T getCode() {
        return code;
    }

    public String getCurrency() {
        return currency;
    }

}
