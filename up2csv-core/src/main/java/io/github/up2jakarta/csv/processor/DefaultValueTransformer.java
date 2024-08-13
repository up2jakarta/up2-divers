package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.exception.ProcessorException;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;

/**
 * Configurable {@link ConfigurableTransformer} used by the annotation
 * {@link io.github.up2jakarta.csv.annotation.Default} to set the default value when input data is <code>null</code>.
 */
public class DefaultValueTransformer extends SingleArgumentTransformer {

    @Override
    public String transform(String value, String defaultValue) throws ProcessorException {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
