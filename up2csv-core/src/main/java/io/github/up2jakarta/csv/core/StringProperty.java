package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.exception.ProcessorException;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;

import java.lang.reflect.Field;

/**
 * Internal representation for {@link String} property of beans.
 */
final class StringProperty extends Property<String> {

    private final ConfigurableTransformer[] transformers;

    StringProperty(Field field, int offset, ConfigurableTransformer[] transformers) throws BeanException {
        super(field, offset);
        this.transformers = transformers;
    }

    @Override
    void setValue(Object bean, String value) throws BeanException {
        for (final ConfigurableTransformer transformer : transformers) {
            try {
                value = transformer.transform(value);
            } catch (Throwable ex) {
                var realType = transformer.getClass();
                if (transformer instanceof TransformerWrapper wrapper) {
                    realType = wrapper.delegate.getClass();
                }
                throw new ProcessorException(realType, field.getName(), ex);
            }
        }
        Beans.setValue(bean, value, setter);
    }

}
