package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.hdl.IErrorCause;
import io.github.up2jakarta.csv.data.Segment;

import java.util.List;

public final class SimpleResult<T extends Segment, E extends IErrorCause<?, ?>> {

    private final T bean;
    private final List<E> errors;

    public SimpleResult(T bean, List<E> errors) {
        this.bean = bean;
        this.errors = errors;
    }

    public T getBean() {
        return bean;
    }

    public List<E> getErrors() {
        return errors;
    }

}
