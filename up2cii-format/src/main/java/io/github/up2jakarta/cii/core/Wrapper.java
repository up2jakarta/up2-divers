package io.github.up2jakarta.cii.core;

public final class Wrapper<T> {

    private T value;

    public Wrapper(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

}
