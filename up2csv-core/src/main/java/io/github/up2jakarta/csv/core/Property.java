package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.DataType;
import io.github.up2jakarta.csv.misc.Beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Internal property representation.
 *
 * @param <V> the value type
 */
abstract class Property<V, D extends DataType<D>> {

    protected final Method setter;
    protected final Field field;
    protected final int offset;
    protected final D type;

    Property(Field field, D type, int offset) throws BeanException {
        this.type = type;
        this.field = field;
        this.offset = offset;
        this.setter = Beans.getAccessibleSetter(field);
    }

    /**
     * Set the given bean property by the given value.
     *
     * @param bean    the bean object
     * @param value   the property value
     * @param offset  the truncated offset
     * @param handler the event handler
     * @throws BeanException if the property is not accessible for write
     */
    abstract void setValue(Object bean, V value, int offset, EventHandler<?, ?, D, ?> handler) throws BeanException;

}
