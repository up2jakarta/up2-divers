package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Beans;

import java.lang.reflect.Field;
import java.util.List;

final class ObjectProperty<T, D extends DataType<D>> extends PositionProperty<T, D> {

    private final Error config;
    private final Conversion<T> adapter;

    ObjectProperty(Field field, D type, int offset, List<ProcessorWrapper<?, D>> processors, Conversion<T> adapter) throws BeanException {
        super(field, type, offset, processors, adapter.converter());
        this.adapter = adapter;
        this.config = field.getAnnotation(Error.class);
    }

    @Override
    String format(T value) {
        if (value != null) {
            return adapter.formatter().apply(value);
        }
        return null;
    }

    @Override
    T parse(Object bean, String value, int offset, EventHandler<?, ?, D, ?> handler) throws BeanException {
        value = process(value, offset, handler);
        if (value == null) {
            return null;
        }
        try {
            final T result = adapter.converter().apply(value);
            if (result != null) {
                Beans.setValue(bean, result, setter);
            }
            return result;
        } catch (BeanException beanException) {
            throw beanException;
        } catch (Throwable error) {
            handler.handleEvent(type, offset + super.offset, error, config, false);
            return null;
        }
    }

}