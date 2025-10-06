package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.DataType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Internal property representation.
 *
 * @param <V> the value type
 */
abstract class Property<V, D extends DataType<D>> {

    protected final int offset;
    protected final D dataType;
    protected final Class<?> fieldType;

    final Method setter;
    final Method getter;
    final Field field;

    Property(Field field, Class<V> fieldType, D dataType, int offset) throws BeanException {
        this.dataType = dataType;
        this.fieldType = fieldType;
        this.field = field;
        this.offset = offset;
        this.setter = Beans.getAccessibleSetter(field);
        this.getter = Beans.getAccessibleGetter(field);
    }

    /**
     * Get the property value of the given bean.
     *
     * @param bean the bean object
     * @return the property value, or else the default value
     * @throws BeanException if the property is not accessible for read
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
