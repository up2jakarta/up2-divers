package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.lang.annotation.Annotation;
import java.util.List;

import static io.github.up2jakarta.csv.core.BeanScanner.getDefault;
import static io.github.up2jakarta.csv.core.EventHandler.ERROR_PROCESSOR;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static io.github.up2jakarta.xml.clv.PropertyException.of;

/**
 * Internal {@link io.github.up2jakarta.csv.cfg.Position} implementation.
 */
abstract class PProperty<T, D extends DataType<D>> extends Property<T, D> {

    final boolean required;
    private final T defaultValue;
    private final List<WProcessor<?, D>> processors;

    private PProperty(Accessor<T> va, D dt, int po, boolean pr, List<WProcessor<?, D>> ps, PropertyConverter<T> pc) throws BeanException {
        super(va, dt, po);
        this.required = pr;
        this.processors = ps;
        this.defaultValue = getDefault(this.getField(), pc);
    }

    private PProperty(PProperty<T, D> source) throws BeanException {
        super(source);
        this.processors = source.processors;
        this.defaultValue = source.defaultValue;
        this.required = source.required;
    }

    String process(String value, int offset, EventHandler<?, D, ?> handler) {
        for (final WProcessor<?, D> processor : processors) {
            try {
                value = processor.process(value);
            } catch (RuntimeException ex) {
                processor.handle(error, dataType, offset + super.offset, ex, handler);
            }
        }
        return value;
    }

    /**
     * Sets and returns the parsed value after processing and parsing the given value.
     *
     * @param bean    the bean object
     * @param value   the property value
     * @param offset  the truncated offset
     * @param handler the event handler
     * @return the parsed value
     * @throws BeanException if the property is not accessible for write
     */
    abstract T parse(Object bean, String value, int offset, EventHandler<?, D, ?> handler) throws BeanException;

    /**
     * Formats the given value for CSV output.
     *
     * @param value the property value
     * @return the formatted sequence
     */
    abstract String format(T value);

    @Override
    final T defaultValue() {
        return defaultValue;
    }

    static final class WProcessor<A extends Annotation, D extends DataType<D>> {
        private final A config;
        private final InputProcessor<A> delegate;
        private final Class<? extends RuntimeException> skip;

        WProcessor(InputProcessor<A> delegate, Class<? extends RuntimeException> skip, A config) {
            this.delegate = delegate;
            this.config = config;
            this.skip = skip;
        }

        void handle(Error error, D type, int offset, RuntimeException exception, EventHandler<?, D, ?> handler) {
            if (error != null) {
                handler.handleEvent(type, offset, of(error.severity(), error.value(), exception), error);
            } else if (exception instanceof PropertyException pException) {
                handler.handleEvent(type, offset, pException, error);
            } else {
                final SeverityType severity = skip.isInstance(exception) ? WARNING : ERROR;
                handler.handleEvent(type, offset, of(severity, ERROR_PROCESSOR, exception), error);
            }
        }

        String process(String value) {
            return delegate.process(value, config);
        }
    }

    /**
     * Internal representation for {@link Object} property.
     */
    static final class POProperty<T, D extends DataType<D>> extends PProperty<T, D> {

        private final Conversion<T> adapter;

        POProperty(Accessor<?> va, D type, int offset, boolean required, List<WProcessor<?, D>> ps, Conversion<T> pc) throws BeanException {
            //noinspection unchecked
            super((Accessor<T>) va, type, offset, required, ps, pc.converter());
            this.adapter = pc;
        }

        POProperty(POProperty<T, D> source) throws BeanException {
            super(source);
            this.adapter = source.adapter;
        }

        @Override
        String format(T value) {
            if (value != null) {
                return adapter.formatter().apply(value);
            }
            return null;
        }

        @Override
        T parse(Object bean, String value, int offset, EventHandler<?, D, ?> handler) throws BeanException {
            value = process(value, offset, handler);
            if (value == null) {
                return null;
            }
            final T result;
            try {
                result = adapter.converter().apply(value);
            } catch (Exception ex) {
                handler.handleEvent(dataType, offset + super.offset, ex, super.error);
                return null;
            }
            return this.setValue(bean, result);
        }

    }

    /**
     * Internal representation for {@link String} property.
     */
    static final class PSProperty<D extends DataType<D>> extends PProperty<String, D> {

        PSProperty(Accessor<String> va, D type, int offset, boolean required, List<WProcessor<?, D>> ps) throws BeanException {
            super(va, type, offset, required, ps, v -> v);
        }

        PSProperty(PSProperty<D> source) throws BeanException {
            super(source);
        }

        @Override
        String parse(Object bean, String value, int offset, EventHandler<?, D, ?> handler) throws BeanException {
            value = process(value, offset, handler);
            return this.setValue(bean, value);
        }

        @Override
        String format(String value) {
            return value;
        }

    }
}
