package io.github.up2jakarta.csv.core.misc;

public class DummyException extends RuntimeException {

    public DummyException(String message) {
        super(message);
    }

    public DummyException(String message, Throwable cause) {
        super(message, cause);
    }

}