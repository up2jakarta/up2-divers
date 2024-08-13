package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.exception.ProcessorException;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;

/**
 * Up2 simple {@link ConfigurableTransformer} within only one argument.
 */
public abstract class SingleArgumentTransformer implements ConfigurableTransformer {

    public abstract String transform(String value, String arg) throws ProcessorException;

    @Override
    public final String transform(String value, String... args) throws ProcessorException {
        return transform(value, args[0]);
    }

}
