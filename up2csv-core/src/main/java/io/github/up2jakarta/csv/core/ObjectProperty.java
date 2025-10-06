package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;

import java.lang.reflect.Field;
import java.util.List;

final class ObjectProperty<T, D extends DataType<D>> extends PositionProperty<T, D> {

    private final Error config;
    private final Conversion<T> adapter;

    ObjectProperty(Field field, Class<?> fieldType, D dataType, int offset, List<ProcessorWrapper<?, D>> processors, Conversion<T> adapter) throws BeanException {
        //noinspection unchecked
        super(field, (Class<T>) fieldType, dataType, offset, processors, adapter.converter());
        this.adapter = adapter;
        this.config = field.getAnnotation(Error.class);
    }

    private T parse(String value, int offset, EventHandler<?, D, ?> handler) {
        if (value == null) {
            return null;
        }
        try {
            return adapter.converter().apply(value);
        } catch (Exception error) {
            handler.handleEvent(dataType, offset + super.offset, error, config);
            return null;
        }
    }

    @Override
    String format(T value) {
        if (value != null) {
            return adapter.formatter().apply(value);
        }
        return null;
    }

    @Override
    T parse(Object bean, String value, int offset, EventHandler<?, D, ?> handler) throws BeanException {
        value = process(value, offset, handler);
        final T result = this.parse(value, offset, handler);
        if (result != null) {
            Beans.setValue(bean, result, setter);
        }
        return result;
    }

}