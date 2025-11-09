package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.DataType;

import java.util.Optional;

import static io.github.up2jakarta.csv.core.hdl.PProperty.POProperty;
import static io.github.up2jakarta.csv.core.hdl.PProperty.PWProperty;

/**
 * Internal {@link io.github.up2jakarta.csv.cfg.Position} implementation.
 */
public abstract sealed class PProperty<T, V, D extends DataType<D>> extends Property<T, V, D> permits POProperty, PWProperty {

    public final boolean required;
    private final Conversion<T> adapter;
    private final PProcessor<D> processor;

    private PProperty(PAccessor<?, V> va, D dt, int fo, Position pp, PProcessor<D> ps, Conversion<T> pc, DefaultValue<T, V, D> dv) throws BeanException {
        super(va, dt, fo, pp, dv);
        this.required = pp.required();
        this.processor = ps;
        this.adapter = pc;
    }

    private PProperty(PProperty<T, V, D> source, V defaultValue) throws BeanException {
        super(source, defaultValue);
        this.processor = source.processor;
        this.required = source.required;
        this.adapter = source.adapter;
    }

    @Override
    protected final String format(V value) {
        final T unwrapped = this.from(value);
        if (unwrapped != null) {
            return adapter.formatter().apply(unwrapped);
        }
        return null;
    }

    @Override
    protected final V parse(String data, int offset, EventHandler<?, D, ?> handler) throws BeanException {
        if (data != null) {
            data = processor.process(data, offset, this, handler);
        } else {
            return defaultValue;
        }
        if (data != null) {
            try {
                final T value = adapter.converter().apply(data);
                return this.wrap(value);
            } catch (Exception cause) {
                handler.handle(dataType, offset + this.offset, cause, this.error);
                return this.wrap(null);
            }
        }
        return null;
    }

    /**
     * Internal representation for {@link Object} property.
     */
    public static final class POProperty<T, D extends DataType<D>> extends PProperty<T, T, D> {

        public POProperty(PAccessor<?, T> va, D type, int fo, Position pp, PProcessor<D> ps, Conversion<T> pc) throws BeanException {
            super(va, type, fo, pp, ps, pc, (p) -> ps.defaultValue(p, pc.converter()));
        }

        public POProperty(POProperty<T, D> source) throws BeanException {
            super(source, source.defaultValue);
        }

        @Override
        protected T wrap(T value) {
            return value;
        }

        @Override
        protected T from(T value) {
            return value;
        }
    }

    /**
     * Internal representation for {@link java.util.Optional} property.
     */
    public static final class PWProperty<T, D extends DataType<D>> extends PProperty<T, Optional<T>, D> {

        public PWProperty(PAccessor<?, Optional<T>> va, D type, int fo, Position pp, PProcessor<D> ps, Conversion<T> pc) throws BeanException {
            super(va, type, fo, pp, ps, pc, (p) -> Optional.ofNullable(ps.defaultValue(p, pc.converter())));
        }

        public PWProperty(PWProperty<T, D> source) throws BeanException {
            super(source, source.defaultValue);
        }

        @Override
        protected Optional<T> wrap(T value) {
            return Optional.ofNullable(value);
        }

        @Override
        protected T from(Optional<T> value) {
            return value.orElse(null);
        }
    }
}
