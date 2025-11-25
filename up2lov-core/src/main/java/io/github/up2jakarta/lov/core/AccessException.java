package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.MessageFormatter;

import static io.github.up2jakarta.lov.core.Beans.getTypeName;

/**
 * Up2J Bean Access Exception that wraps property access or bean creation exceptions.
 */
public class AccessException extends RuntimeException implements MessageFormatter {

    private static final String FORMAT = "%s[%s] - %s";

    private final Class<?> source;
    private final String locator;

    public AccessException(Class<?> type, String locator, Throwable cause) {
        super(cause);
        this.locator = locator;
        this.source = type;
    }

    public AccessException(Class<?> type, String locator, String message) {
        super(message);
        this.locator = locator;
        this.source = type;
    }

    public static <T> T notNull(T bean, Class<?> type, String locator) {
        if (bean == null)
            throw new AccessException(type, locator, "must not be null");
        return bean;
    }

    @Override
    public String getMessage() {
        return getFormattedMessage();
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, getTypeName(source), locator, super.getMessage());
    }

}
