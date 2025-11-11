package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.xml.api.MessageFormatter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;

import static io.github.up2jakarta.csv.core.ext.Beans.getTypeName;

/**
 * Up2 Bean Access Exception that wraps property access or bean creation exceptions.
 */
public class AccessException extends RuntimeException implements MessageFormatter {

    private static final String FORMAT = "%s[%s] - %s";

    private final Class<?> source;
    private final String locator;

    public AccessException(Class<?> type, String locator, Exception cause) {
        super(cause);
        this.locator = locator;
        this.source = type;
    }

    public AccessException(Class<?> type, String locator, String message) {
        super(message);
        this.locator = locator;
        this.source = type;
    }

    public AccessException(Member source, Exception cause) {
        this(source.getDeclaringClass(), source.getName(), cause);
    }

    public AccessException(Member source, String message) {
        this(source.getDeclaringClass(), source.getName(), message);
    }

    public AccessException(Constructor<?> origin, Exception cause) {
        this(origin.getDeclaringClass(), "new", cause);
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
