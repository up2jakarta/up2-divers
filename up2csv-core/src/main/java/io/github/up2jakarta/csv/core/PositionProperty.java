package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.PropertyParser;
import io.github.up2jakarta.csv.misc.BeanException;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Internal representation for {@link String} property of beans.
 */
abstract class PositionProperty<T, D extends DataType<D>> extends Property<T, D> {

    private final T defaultValue;
    private final List<ProcessorWrapper<?, D>> processors;

    PositionProperty(Field field, D type, int offset, List<ProcessorWrapper<?, D>> processors, PropertyParser<T> parser) throws BeanException {
        super(field, type, offset);
        this.processors = processors;
        this.defaultValue = BeanSupport.getDefault(field, parser);
    }

    protected final String process(String value, int offset, EventHandler<?, ?, D, ?> handler) {
        for (final ProcessorWrapper<?, D> processor : processors) {
            try {
                value = processor.process(value);
            } catch (RuntimeException ex) {
                processor.handle(field, type, offset + super.offset, ex, handler);
            }
        }
        return value;
    }

    /**
     * Set the given bean property by the given value.
     *
     * @param bean    the bean object
     * @param value   the property value
     * @param offset  the truncated offset
     * @param handler the event handler
     * @throws BeanException if the property is not accessible for write
     */
    abstract void setValue(Object bean, String value, int offset, EventHandler<?, ?, D, ?> handler) throws BeanException;

    /**
     * Formats the given value for CSV output.
     *
     * @param value the property value
     * @return the formatted sequence
     */
    abstract String format(T value);

    @Override
    final T defaultValue() {
        return defaultValue;
    }

}
