package io.github.up2jakarta.csv.exception;

/**
 * Up2 base of all {@link java.lang.RuntimeException } for Up2CSV engine.
 */
abstract class RuntimeException extends java.lang.RuntimeException implements MessageFormatter {

    protected RuntimeException(final Throwable cause) {
        super(cause);
    }

    protected RuntimeException(final String message) {
        super(message);
    }

}
