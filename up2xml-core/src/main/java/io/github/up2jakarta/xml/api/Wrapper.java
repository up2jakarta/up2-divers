package io.github.up2jakarta.xml.api;

import java.util.Optional;

public final class Wrapper<T> {

    private T value;

    public Wrapper() {
        this.value = null;
    }

    public Wrapper(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public Optional<T> safe() {
        return Optional.ofNullable(value);
    }

}
