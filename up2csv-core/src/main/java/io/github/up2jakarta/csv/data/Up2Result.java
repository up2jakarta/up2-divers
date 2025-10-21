package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IError;

import java.util.List;

public final class Up2Result<T extends Segment, E extends IError<?>> {

    private final T bean;
    private final List<E> errors;

    public Up2Result(T bean, List<E> errors) {
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
