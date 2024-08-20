package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Conversion;

import java.lang.reflect.Field;

final class ConvertedProperty<T> extends PositionProperty<String> {

    private final Conversion<T> parser;

    ConvertedProperty(Field field, int offset, ProcessorWrapper<?>[] processors, Conversion<T> parser) throws BeanException {
        super(field, offset, processors);
        this.parser = parser;
    }

    @Override
    void setValue(Object bean, String value, int offset, EventHandler<?, ?, ?> handler) throws BeanException {
        value = process(value, offset, handler);
        if (value != null) {
            try {
                var converted = parser.apply(value);
                Beans.setValue(bean, converted, setter);
            } catch (BeanException beanException) {
                throw beanException;
            } catch (Throwable error) {
                final Error config = field.getAnnotation(Error.class);
                handler.handleEvent(offset + super.offset, error, config, false);
            }
        }
    }

}