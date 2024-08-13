package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Internal property representation.
 *
 * @param <V> the value type
 */
abstract class Property<V> {

    protected final Field field;
    protected final Method setter;
    private final int offset;

    Property(Field field, int offset) throws BeanException {
        this.field = field;
        this.offset = offset;
        this.setter = Beans.getAccessibleSetter(field);
    }

    /**
     * @return the property index
     */
    final int getOffset() {
        return offset;
    }

    /**
     * @return the property field
     */
    final Field getField() {
        return field;
    }

    /**
     * Set the given bean property by the given value.
     *
     * @param bean  the bean object
     * @param value the property value
     * @throws BeanException if the property is not accessible for write
     */
    abstract void setValue(Object bean, V value) throws BeanException;

}
