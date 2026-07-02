package io.github.up2jakarta.lov.core;

import java.lang.reflect.Member;

import static io.github.up2jakarta.lov.core.Beans.getTypeName;

/**
 * Up2J Bean Access Exception that wraps property access or bean creation exceptions.
 */
public final class AccessException extends RuntimeException implements Localizable {

    private final Class<?> source;
    private final String locator;

    public AccessException(Class<?> type, String locator, String message) {
        super(message);
        this.locator = locator;
        this.source = type;
    }

    public AccessException(Class<?> type, String locator, Throwable cause) {
        super(cause.getMessage(), cause);
        this.locator = locator;
        this.source = type;
    }

    public AccessException(Class<?> type, String locator, String message, Throwable cause) {
        super(message, cause);
        this.locator = locator;
        this.source = type;
    }

    public AccessException(Class<?> source, String message) {
        this(source, CLASS, message);
    }

    public AccessException(Member source, String message) {
        this(source.getDeclaringClass(), source.getName(), message);
    }

    public AccessException(Member source, Throwable cause) {
        this(source.getDeclaringClass(), source.getName(), cause);
    }

    public AccessException(TypeContext context, String message) {
        this(context.source, message);
    }

    public AccessException(TypeContext context, Throwable cause) {
        this(context.source, cause);
    }

    public static <T> T notNull(T bean, Class<?> type, String locator) {
        if (bean == null) {
            throw new AccessException(type, locator, "must not be null");
        }
        return bean;
    }

    @Override
    public Class<?> getSource() {
        return source;
    }

    @Override
    public String getName() {
        return getTypeName(source);
    }

    @Override
    public String getLocator() {
        return locator;
    }

    @Override
    public String getLocalizedMessage() {
        return String.format(FORMAT, this.getName(), locator, super.getMessage());
    }

}
