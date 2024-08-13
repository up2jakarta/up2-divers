package io.github.up2jakarta.csv.test;

import io.github.up2jakarta.csv.exception.ProcessorException;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;
import org.springframework.stereotype.Component;

@Component
public class DummyTransformer implements ConfigurableTransformer {

    @SuppressWarnings("deprecation")
    public String transform(String value, String... args) throws ProcessorException {
        if (args.length == 0) {
            throw new UnsupportedOperationException(value);
        }
        throw new ProcessorException(getClass(), "Override", args[0]);
    }

}
