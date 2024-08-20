package io.github.up2jakarta.csv.exception;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Up2 Bean Exception for {@link io.github.up2jakarta.csv.core.MapperFactory#build(Class)}.
 */
public class BeanException extends Exception implements MessageFormatter {

    private static final String FORMAT = "%s[%s] - %s";

    private final Class<?> beanType;
    private final String attribute;

    public BeanException(final Class<?> beanType, final String attribute, final String message) {
        super(message);
        this.beanType = beanType;
        this.attribute = attribute;
    }

    public BeanException(final Class<?> beanType, final Field attribute, final String message) {
        this(beanType, attribute.getName(), message);
    }

    public BeanException(final Class<?> beanType, final Method attribute, final String message) {
        this(beanType, attribute.getName(), message);
    }

    public BeanException(final Field attribute, final String message) {
        this(attribute.getDeclaringClass(), attribute.getName(), message);
    }

    public BeanException(final Method attribute, final String message) {
        this(attribute.getDeclaringClass(), attribute.getName(), message);
    }

    public BeanException(final Class<?> bean, final String message) {
        this(bean, "class", message);
    }

    /**
     * @return the bean class
     */
    public final Class<?> getBeanType() {
        return beanType;
    }

    /**
     * @return the attribute of the bean, it should be the property name or whatever.
     */
    public final String getAttribute() {
        return attribute;
    }

    @Override
    public String getMessage() {
        return getFormattedMessage();
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, beanType.getSimpleName(), attribute, super.getMessage());
    }

}
