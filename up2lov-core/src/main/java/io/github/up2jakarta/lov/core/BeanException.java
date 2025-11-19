package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.MessageFormatter;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

/**
 * Up2 Bean Exception that wraps missing or wrong configuration on java-beans.
 */
public class BeanException extends Exception implements MessageFormatter {

    private static final String FORMAT = "%s[%s] - %s";

    private final AnnotatedElement source;
    private final CharSequence name;
    private final String locator;

    private BeanException(AnnotatedElement source, CharSequence name, String locator, String message) {
        super(message);
        this.source = source;
        this.name = name;
        this.locator = locator;
    }

    public BeanException(Class<?> source, String locator, final String message) {
        this(source, Beans.getTypeName(source), locator, message);
    }

    public BeanException(Member source, String message) {
        this(source.getDeclaringClass(), source.getName(), message);
    }

    public BeanException(Class<?> source, String message) {
        this(source, "class", message);
    }

    public BeanException(Package source, String message) {
        this(source, "info", source.getName(), message);
    }

    public static BeanException of(AnnotatedElement source, String message) {
        if (source instanceof Member m) {
            return new BeanException(m, message);
        }
        if (source instanceof Package p) {
            return new BeanException(p, message);
        }
        if (source instanceof Class<?> c) {
            return new BeanException(c, message);
        }
        throw new IllegalArgumentException(source.getClass().getName());
    }

    /**
     * @return the bean class
     */
    public final AnnotatedElement getSource() {
        return source;
    }

    /**
     * @return the attribute of the bean, it should be the property name or whatever.
     */
    public final String getLocator() {
        return locator;
    }

    @Override
    public String getMessage() {
        return getFormattedMessage();
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, name, locator, super.getMessage());
    }

}
