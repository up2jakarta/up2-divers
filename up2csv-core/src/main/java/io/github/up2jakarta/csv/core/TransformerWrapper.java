package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Processor;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.exception.ProcessorException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.ConfigurableTransformer;
import io.github.up2jakarta.csv.processor.NoArgumentTransformer;

/**
 * Wrapper for {@link ConfigurableTransformer} that has a factory method {@link #of(BeanContext, Processor, String[])}.
 */
final class TransformerWrapper extends NoArgumentTransformer {

    final ConfigurableTransformer delegate;
    private final String[] arguments;

    private TransformerWrapper(ConfigurableTransformer delegate, String[] arguments) {
        this.delegate = delegate;
        this.arguments = arguments;
    }

    /**
     * Factory method for create transformer from the given processor annotation.
     *
     * @param context   the bean context
     * @param processor the processor annotation
     * @param args      the overriding arguments
     * @return a preconfigured transformer
     * @throws BeanException if the transformer in not managed by the given context
     */
    static ConfigurableTransformer of(BeanContext context, Processor processor, String... args) throws BeanException {
        final ConfigurableTransformer delegate = Beans.getBean(context, processor.value());
        if (args.length == 0) {
            args = processor.arguments();
        }
        if (args.length == 0) {
            return delegate;
        }
        return new TransformerWrapper(delegate, args);
    }

    @Override
    public String transform(String value) throws ProcessorException {
        return delegate.transform(value, arguments);
    }

}
