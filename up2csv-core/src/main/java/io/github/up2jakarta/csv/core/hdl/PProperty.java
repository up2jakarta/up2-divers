package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import static io.github.up2jakarta.csv.core.hdl.PProperty.POProperty;
import static io.github.up2jakarta.csv.core.hdl.PProperty.PSProperty;

/**
 * Internal {@link io.github.up2jakarta.csv.cfg.Position} implementation.
 */
public abstract sealed class PProperty<T, D extends DataType<D>> extends Property<T, D> permits POProperty, PSProperty {

    public final boolean required;
    private final PProcessor<D> processor;

    private PProperty(PAccessor<?, T> va, D dt, int fo, Position pp, PProcessor<D> ps, PropertyConverter<T> pc) throws BeanException {
        super(va, dt, fo, pp, (p) -> ps.defaultValue((PProperty<T, D>) p, pc));
        this.required = pp.required();
        this.processor = ps;
    }

    private PProperty(PProperty<T, D> source) throws BeanException {
        super(source, source.defaultValue);
        this.processor = source.processor;
        this.required = source.required;
    }

    public final String format(Segment bean, boolean prototype) throws BeanException {
        if (prototype && defaultValue != null) {
            return this.format(defaultValue);
        }
        return this.format(bean);
    }

    public final String format(Segment bean) throws BeanException {
        final T value = this.get(bean);
        if (value != null) {
            return this.format(value);
        }
        return null;
    }

    /**
     * Parses and returns the property-value of the specified flat-value within processing and default-value.
     *
     * @param value   the property value
     * @param offset  the truncated offset
     * @param handler the event handler
     * @return the parsed value
     * @throws BeanException if the property is not accessible for write
     */
    public final T get(String value, int offset, EventHandler<?, D, ?> handler) throws BeanException {
        if (value != null) {
            value = processor.process(value, offset, this, handler);
        } else {
            return defaultValue;
        }
        return (value != null) ? this.parse(value, offset, handler) : null;
    }

    /**
     * Parses and returns the property-value of the specified flat-value without processing and default-value.
     *
     * @param value   the property value
     * @param offset  the truncated offset
     * @param handler the event handler
     * @return the parsed value
     * @throws BeanException if the property is not accessible for write
     */
    public abstract T parse(String value, int offset, EventHandler<?, D, ?> handler) throws BeanException;

    /**
     * Formats the given value for CSV output.
     *
     * @param value the value to format
     * @return the formatted sequence
     */
    abstract String format(T value);

    /**
     * Internal representation for {@link Object} property.
     */
    public static final class POProperty<T, D extends DataType<D>> extends PProperty<T, D> {
        private final Conversion<T> adapter;

        public POProperty(PAccessor<?, ?> va, D type, int fo, Position pp, PProcessor<D> ps, Conversion<T> pc) throws BeanException {
            //noinspection unchecked
            super((PAccessor<?, T>) va, type, fo, pp, ps, pc.converter());
            this.adapter = pc;
        }

        public POProperty(POProperty<T, D> source) throws BeanException {
            super(source);
            this.adapter = source.adapter;
        }

        @Override
        public T parse(String value, int offset, EventHandler<?, D, ?> handler) {
            try {
                return adapter.converter().apply(value);
            } catch (Exception cause) {
                handler.handle(dataType, offset + this.offset, cause, super.error);
                return null;
            }
        }

        @Override
        String format(T value) {
            return adapter.formatter().apply(value);
        }
    }

    /**
     * Internal representation for {@link String} property.
     */
    public static final class PSProperty<D extends DataType<D>> extends PProperty<String, D> {
        public PSProperty(PAccessor<?, String> va, D type, int fo, Position pp, PProcessor<D> ps) throws BeanException {
            super(va, type, fo, pp, ps, v -> v);
        }

        public PSProperty(PSProperty<D> source) throws BeanException {
            super(source);
        }

        @Override
        public String parse(String value, int offset, EventHandler<?, D, ?> handler) {
            return value;
        }

        @Override
        String format(String value) {
            return value;
        }
    }
}
