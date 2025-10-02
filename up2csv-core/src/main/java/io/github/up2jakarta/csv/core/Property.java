package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.misc.BeanException;
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
    protected final Method getter;
    protected final Field field;
    protected final int offset;
    protected final D type;

    Property(Field field, D type, int offset) throws BeanException {
        this.type = type;
        this.field = field;
        this.offset = offset;
        this.setter = Beans.getAccessibleSetter(field);
        this.getter = Beans.getAccessibleGetter(field);
    }

    /**
     * Get the formatted value of the given property.
     *
     * @param bean the bean object
     * @return the formatted value
     * @throws BeanException f the property is not accessible for read
     */
    final V getValue(Object bean) throws BeanException {
        //noinspection unchecked
        final V value = (V) Beans.getValue(bean, getter);
        if (value != null) {
            return value;
        }
        return this.defaultValue();
    }

    /**
     * Gets the default value if configured, or else returns {@link null}.
     *
     * @return the default value
     * @throws BeanException if the property is not accessible for write
     */
    abstract V defaultValue() throws BeanException;

}
