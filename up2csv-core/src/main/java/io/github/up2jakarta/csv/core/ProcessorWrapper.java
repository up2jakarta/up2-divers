package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import io.github.up2jakarta.csv.extension.DataType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.core.Mapper.LOGGER;
import static io.github.up2jakarta.csv.extension.SeverityType.ERROR;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_PROCESSOR;

/**
 * Wrapper for {@link ConfigurableProcessor}.
 */
final class ProcessorWrapper<A extends Annotation, D extends DataType<D>> {

    private final ConfigurableProcessor<A> delegate;
    private final Class<? extends RuntimeException> skip;
    private final A config;

    ProcessorWrapper(ConfigurableProcessor<A> delegate, Class<? extends RuntimeException> skip, A config) {
        this.delegate = delegate;
        this.config = config;
        this.skip = skip;
    }

    void handle(Field field, D type, int offset, RuntimeException exception, EventHandler<?, ?, D, ?> handler) {
        if (skip.isInstance(exception)) {
            LOGGER.warn("Skip @Processor[{}] error : {}", delegate.getClass().getSimpleName(), exception.getMessage());
        } else {
            final Error c = field.getAnnotation(Error.class);
            if (c != null) {
                handler.handleEvent(type, offset, PropertyException.of(c.severity(), c.value(), exception), c, true);
            } else if (exception instanceof PropertyException pException) {
                handler.handleEvent(type, offset, pException, c, true);
            } else {
                handler.handleEvent(type, offset, PropertyException.of(ERROR, ERROR_PROCESSOR, exception), c, true);
            }
        }
    }

    String process(String value) {
        return delegate.process(value, config);
    }

}
