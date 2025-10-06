package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.cfg.Required;
import io.github.up2jakarta.csv.data.DataType;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Internal representation for {@link String} property of beans.
 */
abstract class PositionProperty<T, D extends DataType<D>> extends Property<T, D> {

    final boolean required;
    private final T defaultValue;
    private final List<ProcessorWrapper<?, D>> processors;

    PositionProperty(Field field, Class<T> fieldType, D dataType, int offset, List<ProcessorWrapper<?, D>> processors, PropertyConverter<T> parser) throws BeanException {
        super(field, fieldType, dataType, offset);
        this.processors = processors;
        this.defaultValue = BeanSupport.getDefault(field, parser);
        this.required = field.isAnnotationPresent(Required.class);
    }

    protected final String process(String value, int offset, EventHandler<?, D, ?> handler) {
        for (final ProcessorWrapper<?, D> processor : processors) {
            try {
                value = processor.process(value);
            } catch (RuntimeException ex) {
                processor.handle(field, dataType, offset + super.offset, ex, handler);
            }
        }
        return value;
    }

    /**
     * Sets and returns the parsed value after processing and parsing the given value.
     *
     * @param bean    the bean object
     * @param value   the property value
     * @param offset  the truncated offset
     * @param handler the event handler
     * @return the parsed value
     * @throws BeanException if the property is not accessible for write
     */
    abstract T parse(Object bean, String value, int offset, EventHandler<?, D, ?> handler) throws BeanException;

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
