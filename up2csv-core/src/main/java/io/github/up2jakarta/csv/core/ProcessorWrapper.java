package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.PropertyException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.misc.Errors.ERROR_PROCESSOR;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;

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
        final SeverityType severity = skip.isInstance(exception) ? WARNING : ERROR;
        final Error c = field.getAnnotation(Error.class);
        if (c != null) {
            handler.handleEvent(type, offset, PropertyException.of(c.severity(), c.value(), exception), c, true);
        } else if (exception instanceof PropertyException pException) {
            handler.handleEvent(type, offset, pException, c, true);
        } else {
            handler.handleEvent(type, offset, PropertyException.of(severity, ERROR_PROCESSOR, exception), c, true);
        }
    }

    String process(String value) {
        return delegate.process(value, config);
    }

}
