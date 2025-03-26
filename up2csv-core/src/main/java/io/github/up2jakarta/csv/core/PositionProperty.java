package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.misc.BeanException;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Internal representation for {@link String} property of beans.
 */
abstract class PositionProperty<T, D extends DataType<D>> extends Property<T, D> {

    private final List<ProcessorWrapper<?, D>> processors;

    PositionProperty(Field field, D type, int offset, List<ProcessorWrapper<?, D>> processors) throws BeanException {
        super(field, type, offset);
        this.processors = processors;
    }

    final String process(String value, int offset, EventHandler<?, ?, D, ?> handler) {
        for (final ProcessorWrapper<?, D> processor : processors) {
            try {
                value = processor.process(value);
            } catch (RuntimeException ex) {
                processor.handle(field, type, offset + super.offset, ex, handler);
            }
        }
        return value;
    }

}
