package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.xml.api.MessageFormatter;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Up2 Bean Exception for {@link Up2Factory#build(Class)}.
 */
public class BeanException extends Exception implements MessageFormatter {

    private static final String FORMAT = "%s[%s] - %s";

    private final AnnotatedElement source;
    private final String sourceName;
    private final String locator;

    private BeanException(AnnotatedElement source, String sourceName, String locator, String message) {
        super(message);
        this.source = source;
        this.sourceName = sourceName;
        this.locator = locator;
    }

    public BeanException(final Class<?> source, final String locator, final String message) {
        this(source, source.getSimpleName(), locator, message);
    }

    public BeanException(final Class<?> source, final Field locator, final String message) {
        this(source, locator.getName(), message);
    }

    public BeanException(final Class<?> source, final Method locator, final String message) {
        this(source, locator.getName(), message);
    }

    public BeanException(final Field source, final String message) {
        this(source.getDeclaringClass(), source.getName(), message);
    }

    public BeanException(final Method source, final String message) {
        this(source.getDeclaringClass(), source.getName(), message);
    }

    public BeanException(final Class<?> source, final String message) {
        this(source, "class", message);
    }

    public BeanException(final Package source, final String message) {
        this(source, "package-info", source.getName(), message);
    }

    public static BeanException of(AnnotatedElement source, String message) {
        if (source instanceof Field f) {
            return new BeanException(f, message);
        }
        if (source instanceof Method m) {
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
        return String.format(FORMAT, sourceName, locator, super.getMessage());
    }

}
