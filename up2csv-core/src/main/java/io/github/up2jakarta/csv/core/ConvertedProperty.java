package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Beans;

import java.lang.reflect.Field;
import java.util.List;

final class ConvertedProperty<T, D extends DataType<D>> extends PositionProperty<String, D> {

    private final Error config;
    private final Conversion<T> parser;

    ConvertedProperty(Field field, D type, int offset, List<ProcessorWrapper<?, D>> processors, Conversion<T> parser) throws BeanException {
        super(field, type, offset, processors);
        this.parser = parser;
        this.config = field.getAnnotation(Error.class);
    }

    @Override
    void setValue(Object bean, String value, int offset, EventHandler<?, ?, D, ?> handler) throws BeanException {
        value = process(value, offset, handler);
        if (value != null) {
            try {
                var converted = parser.apply(value);
                Beans.setValue(bean, converted, setter);
            } catch (BeanException beanException) {
                throw beanException;
            } catch (Throwable error) {
                handler.handleEvent(type, offset + super.offset, error, config, false);
            }
        }
    }

}