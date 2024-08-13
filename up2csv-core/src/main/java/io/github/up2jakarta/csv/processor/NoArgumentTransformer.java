package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.exception.ProcessorException;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;

/**
 * Up2 simple {@link ConfigurableTransformer} without arguments.
 */
public abstract class NoArgumentTransformer implements ConfigurableTransformer {

    public abstract String transform(String value) throws ProcessorException;

    @Override
    public final String transform(String value, String... ignore) throws ProcessorException {
        return transform(value);
    }

}
