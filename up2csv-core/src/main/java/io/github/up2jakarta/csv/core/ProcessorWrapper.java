package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.core.Mapper.LOGGER;
import static io.github.up2jakarta.csv.extension.SeverityType.ERROR;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_PROCESSOR;

/**
 * Wrapper for {@link ConfigurableProcessor}.
 */
final class ProcessorWrapper<A extends Annotation> {

    private final ConfigurableProcessor<A> delegate;
    private final Class<? extends RuntimeException> skip;
    private final A config;

    ProcessorWrapper(ConfigurableProcessor<A> delegate, Class<? extends RuntimeException> skip, A config) {
        this.delegate = delegate;
        this.config = config;
        this.skip = skip;
    }

    void handle(Field field, int offset, RuntimeException exception, EventHandler<?, ?, ?> handler) {
        if (skip.isInstance(exception)) {
            LOGGER.warn("Skip @Processor[{}] error : {}", delegate.getClass().getSimpleName(), exception.getMessage());
        } else {
            final Error c = field.getAnnotation(Error.class);
            if (c != null) {
                handler.handleEvent(offset, PropertyException.of(c.severity(), c.value(), exception), c, true);
            } else if (exception instanceof PropertyException pException) {
                handler.handleEvent(offset, pException, c, true);
            } else {
                handler.handleEvent(offset, PropertyException.of(ERROR, ERROR_PROCESSOR, exception), c, true);
            }
        }
    }

    String process(String value) {
        return delegate.process(value, config);
    }

}
