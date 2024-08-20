package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;

import java.lang.reflect.Field;

/**
 * Internal representation for {@link String} property of beans.
 */
final class StringProperty extends PositionProperty<String> {

    StringProperty(Field field, int offset, ProcessorWrapper<?>[] processors) throws BeanException {
        super(field, offset, processors);
    }

    @Override
    void setValue(Object bean, String value, int offset, EventHandler<?, ?, ?> handler) throws BeanException {
        value = process(value, offset, handler);
        Beans.setValue(bean, value, setter);
    }

}
