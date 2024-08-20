package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;

import java.lang.reflect.Field;

/**
 * Internal representation for {@link String} property of beans.
 */
abstract class PositionProperty<T> extends Property<T> {

    private final ProcessorWrapper<?>[] processors;

    PositionProperty(Field field, int offset, ProcessorWrapper<?>[] processors) throws BeanException {
        super(field, offset);
        this.processors = processors;
    }

    final String process(String value, int offset, EventHandler<?, ?, ?> handler) {
        for (final ProcessorWrapper<?> processor : processors) {
            try {
                value = processor.process(value);
            } catch (RuntimeException ex) {
                processor.handle(field, offset + super.offset, ex, handler);
            }
        }
        return value;
    }

}
