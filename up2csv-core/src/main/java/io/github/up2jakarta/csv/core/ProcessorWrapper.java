package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.core.Errors.ERROR_PROCESSOR;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;

/**
 * Wrapper for {@link InputProcessor}.
 */
final class ProcessorWrapper<A extends Annotation, D extends DataType<D>> {

    private final InputProcessor<A> delegate;
    private final Class<? extends RuntimeException> skip;
    private final A config;

    ProcessorWrapper(InputProcessor<A> delegate, Class<? extends RuntimeException> skip, A config) {
        this.delegate = delegate;
        this.config = config;
        this.skip = skip;
    }

    void handle(Field field, D type, int offset, RuntimeException exception, EventHandler<?, D, ?> handler) {
        final SeverityType severity = skip.isInstance(exception) ? WARNING : ERROR;
        final Error c = field.getAnnotation(Error.class);
        if (c != null) {
            handler.handleEvent(type, offset, PropertyException.of(c.severity(), c.value(), exception), c);
        } else if (exception instanceof PropertyException pException) {
            handler.handleEvent(type, offset, pException, c);
        } else {
            handler.handleEvent(type, offset, PropertyException.of(severity, ERROR_PROCESSOR, exception), c);
        }
    }

    String process(String value) {
        return delegate.process(value, config);
    }

}
