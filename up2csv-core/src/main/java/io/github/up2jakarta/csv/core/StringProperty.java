package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.DataType;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Internal representation for {@link String} property of beans.
 */
final class StringProperty<D extends DataType<D>> extends PositionProperty<String, D> {

    StringProperty(Field field, D type, int offset, List<ProcessorWrapper<?, D>> processors) throws BeanException {
        super(field, type, offset, processors);
    }

    @Override
    void setValue(Object bean, String value, int offset, EventHandler<?, ?, D, ?> handler) throws BeanException {
        value = process(value, offset, handler);
        Beans.setValue(bean, value, setter);
    }

}
