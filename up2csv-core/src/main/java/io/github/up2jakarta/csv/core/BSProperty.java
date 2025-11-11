package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.api.ext.PropertyFormatter;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.BSOperator.BAccessor.BPAccessor;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.core.hdl.FastHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.AccessType;

import java.lang.reflect.*;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.BSProperty.FProperty.FOProperty;
import static io.github.up2jakarta.csv.core.BSProperty.FProperty.FWProperty;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PFAccessor;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PFAccessor.FRAccessor;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PFAccessor.FWAccessor;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PPAccessor;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PPAccessor.*;
import static io.github.up2jakarta.csv.core.BSProperty.PProperty.POProperty;
import static io.github.up2jakarta.csv.core.BSProperty.PProperty.PWProperty;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static jakarta.persistence.AccessType.PROPERTY;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * Internal property representation.
 */
abstract sealed class BSProperty<T, V, D extends DataType<D>> permits BSProperty.FProperty, BSProperty.PProperty {
    final int offset;
    final D dataType;
    final Error error;
    final V defaultValue;
    private final PAccessor<?, V> accessor;

    private BSProperty(PAccessor<?, V> accessor, D dataType, int offset, DefaultValue<T, V, D> dvs) throws BeanException {
        this.error = accessor.source.getAnnotation(Error.class);
        this.offset = offset;
        this.dataType = dataType;
        this.accessor = accessor;
        this.defaultValue = dvs.get(this);
    }

    BSProperty(BSProperty<T, ?, D> source, PAccessor<?, V> accessor, V defaultValue) {
        this.defaultValue = defaultValue;
        this.dataType = source.dataType;
        this.offset = source.offset;
        this.error = source.error;
        this.accessor = accessor;
    }

    BSProperty(BSProperty<T, V, D> source, V defaultValue) throws BeanException {
        this(source, source.accessor.reverse(null), defaultValue);
    }

    BSProperty(PAccessor<?, V> accessor, D dataType, int offset, Fragment fp, V defaultValue) throws BeanException {
        this(accessor, dataType, offset + fp.value(), (p) -> defaultValue);
    }

    BSProperty(PAccessor<?, V> va, D dt, int fo, Position pp, DefaultValue<T, V, D> dv) throws BeanException {
        this(va, dt, fo + pp.value(), dv);
    }

    static BPAccessor id(List<? extends FProperty<Segment, ?, ?>> fs, PProperty<Object, Object, ?> pp) throws BeanException {
        final PAccessor<?, Object> getter = ((BSProperty<Object, Object, ?>) pp).accessor;
        if (Modifier.isFinal(getter.source.getModifiers())) {
            return BPAccessor.of(fs, pp, new PNAccessor<>(Segment.class, pp.type, null));
        }
        return BPAccessor.of(fs, pp, getter.reverse(AccessMode.WO));
    }

    abstract Class<T> getType();

    abstract V wrap(T value);

    abstract T from(V value);

    final String getName() {
        return accessor.source.getName();
    }

    abstract BSProperty<T, V, D> reverse() throws BeanException;

    final AnnotatedElement getSource() {
        return accessor.source;
    }

    final T getValue(Segment bean) {
        final V wrapped = accessor.value(bean, null);
        if (wrapped != null) {
            return this.from(wrapped);
        }
        return null;
    }

    final V value(Segment bean) {
        if (bean == null) {
            return defaultValue;
        }
        return accessor.value(bean, defaultValue);
    }

    final void value(Segment bean, V value) {
        if (value != null) {
            accessor.value(bean, value);
        }
    }

    @Override
    public final String toString() {
        return this.getName();
    }

    @FunctionalInterface
    private interface DefaultValue<T, V, D extends DataType<D>> {
        V get(BSProperty<T, V, D> property) throws BeanException;
    }

    @FunctionalInterface
    interface PProcessor<D extends DataType<D>> {

        default <T> T defaultValue(BSProperty<T, ?, D> pp, PropertyConverter<T> pc) throws BeanException {
            try {
                var v = this.process(null, 0, (PProperty<?, ?, D>) pp, FastHandler.of(ERROR));
                if (v != null) {
                    return pc.apply(v);
                }
                return null;
            } catch (Exception ex) {
                throw BeanException.of(pp.getSource(), "@Position[defaultValue] cannot be parsed");
            }
        }

        String process(String value, int offset, PProperty<?, ?, D> property, EventHandler<D> handler);

    }

    /**
     * Internal {@link Fragment} implementation.
     */
    abstract sealed static class FProperty<S extends Segment, V, B extends DataType<B>> extends BSProperty<S, V, B> permits FOProperty, FWProperty {
        final BSNode<S, B> node;

        private FProperty(BSNode<S, B> node, PAccessor<?, V> va, B type, int offset, Fragment pf, V dv) throws BeanException {
            super(va, type, offset, pf, dv);
            this.node = node;
        }

        private FProperty(BSNode<S, B> node, FProperty<S, V, B> source, V defaultValue) throws BeanException {
            super(source, defaultValue);
            this.node = node;
        }

        @Override
        final Class<S> getType() {
            return node.type;
        }

        @Override
        abstract FProperty<S, V, B> reverse() throws BeanException;

        final S getUnwrapped(Segment bean) {
            return this.from(this.value(bean));
        }

        static final class FOProperty<S extends Segment, B extends DataType<B>> extends FProperty<S, S, B> {
            FOProperty(BSNode<S, B> node, PAccessor<?, S> va, B type, int offset, Fragment pf) throws BeanException {
                super(node, va, type, offset, pf, null);
            }

            private FOProperty(BSNode<S, B> node, FProperty<S, S, B> source) throws BeanException {
                super(node, source, null);
            }

            @Override
            S wrap(S value) {
                return value;
            }

            @Override
            S from(S value) {
                return value;
            }

            @Override
            FOProperty<S, B> reverse() throws BeanException {
                return new FOProperty<>(node.reverse(), this);
            }
        }

        static final class FWProperty<S extends Segment, B extends DataType<B>> extends FProperty<S, Optional<S>, B> {
            FWProperty(BSNode<S, B> n, PAccessor<?, Optional<S>> a, B pt, int po, Fragment pf) throws BeanException {
                super(n, a, pt, po, pf, empty());
            }

            private FWProperty(BSNode<S, B> node, FWProperty<S, B> source) throws BeanException {
                super(node, source, empty());
            }

            @Override
            Optional<S> wrap(S value) {
                return ofNullable(value);
            }

            @Override
            S from(Optional<S> value) {
                return value.orElse(null);
            }

            @Override
            FWProperty<S, B> reverse() throws BeanException {
                return new FWProperty<>(node.reverse(), this);
            }
        }
    }

    /**
     * Internal {@link Position} implementation.
     */
    abstract sealed static class PProperty<T, V, D extends DataType<D>> extends BSProperty<T, V, D> permits POProperty, PWProperty {
        final Class<T> type;
        final boolean required;
        private final PProcessor<D> processor;
        private final PropertyFormatter<T> formatter;
        private final PropertyConverter<T> converter;

        private PProperty(PAccessor<?, V> va, D dt, int fo, Position pp, PProcessor<D> ps, Conversion<T> pc, DefaultValue<T, V, D> dv) throws BeanException {
            super(va, dt, fo, pp, dv);
            this.required = pp.required();
            this.formatter = pc.formatter();
            this.converter = pc.converter();
            this.type = pc.type();
            this.processor = ps;
        }

        private PProperty(PProperty<T, V, D> source, V defaultValue) throws BeanException {
            super(source, defaultValue);
            this.required = source.required;
            this.processor = source.processor;
            this.formatter = source.formatter;
            this.converter = source.converter;
            this.type = source.type;
        }

        @Override
        final Class<T> getType() {
            return type;
        }

        final String format(V value) {
            final T unwrapped = this.from(value);
            if (unwrapped != null) {
                return formatter.apply(unwrapped);
            }
            return null;
        }

        final V parse(String data, int offset, EventHandler<D> handler) {
            if (data != null) {
                data = processor.process(data, offset, this, handler);
            } else {
                return defaultValue;
            }
            if (data != null) {
                try {
                    final T value = converter.apply(data);
                    return this.wrap(value);
                } catch (Exception cause) {
                    handler.handle(dataType, offset + this.offset, cause, this.error);
                    return this.wrap(null);
                }
            }
            return null;
        }

        final String getFormatted(Segment bean) {
            final V value = this.value(bean);
            return this.format(value);
        }

        final String getFormatted(Segment bean, V defaultValue, boolean prototype) {
            if (prototype && defaultValue != null) {
                return this.format(defaultValue);
            }
            return this.getFormatted(bean);
        }

        final T setValue(PAccessor<?, V> setter, Segment bean, T value) {
            final V wrapped = this.wrap(value);
            if (wrapped != null) {
                setter.value(bean, wrapped);
            }
            return value;
        }

        @Override
        abstract PProperty<T, V, D> reverse() throws BeanException;

        /**
         * Internal representation for {@link Object} property.
         */
        static final class POProperty<T, D extends DataType<D>> extends PProperty<T, T, D> {
            POProperty(PAccessor<?, T> va, D type, int fo, Position pp, PProcessor<D> ps, Conversion<T> pc) throws BeanException {
                super(va, type, fo, pp, ps, pc, (p) -> ps.defaultValue(p, pc.converter()));
            }

            private POProperty(POProperty<T, D> source) throws BeanException {
                super(source, source.defaultValue);
            }

            @Override
            T wrap(T value) {
                return value;
            }

            @Override
            T from(T value) {
                return value;
            }

            @Override
            POProperty<T, D> reverse() throws BeanException {
                return new POProperty<>(this);
            }
        }

        /**
         * Internal representation for {@link Optional <Object>} property.
         */
        static final class PWProperty<T, D extends DataType<D>> extends PProperty<T, Optional<T>, D> {
            PWProperty(PAccessor<?, Optional<T>> va, D type, int fo, Position pp, PProcessor<D> ps, Conversion<T> pc) throws BeanException {
                super(va, type, fo, pp, ps, pc, (p) -> Optional.ofNullable(ps.defaultValue(p, pc.converter())));
            }

            private PWProperty(PWProperty<T, D> source) throws BeanException {
                super(source, source.defaultValue);
            }

            @Override
            Optional<T> wrap(T value) {
                return Optional.ofNullable(value);
            }

            @Override
            T from(Optional<T> value) {
                return value.orElse(null);
            }

            @Override
            PWProperty<T, D> reverse() throws BeanException {
                return new PWProperty<>(this);
            }
        }
    }

    /**
     * Internal property Accessor.
     */
    abstract sealed static class PAccessor<S extends AnnotatedElement & Member, V> permits PFAccessor, PPAccessor {
        final S source;

        PAccessor(S source) {
            this.source = source;
        }

        abstract V value(Segment bean, V value) throws AccessException;

        abstract PAccessor<?, V> reverse(AccessMode mode) throws BeanException;

        abstract sealed static class PFAccessor<V> extends PAccessor<Field, V> permits FWAccessor, FRAccessor {
            private PFAccessor(Field source) {
                super(source);
                setAccessible(source);
            }

            static final class FWAccessor<V> extends PFAccessor<V> {
                FWAccessor(Field field) {
                    super(field);
                }

                @Override
                V value(Segment bean, V value) {
                    Beans.setValue(bean, value, source);
                    return value;
                }

                @Override
                PFAccessor<V> reverse(AccessMode mode) {
                    if (mode == AccessMode.WO) {
                        return this;
                    }
                    return new FRAccessor<>(source);
                }
            }

            static final class FRAccessor<V> extends PFAccessor<V> {
                FRAccessor(Field field) {
                    super(field);
                }

                @Override
                V value(Segment bean, V defaultValue) {
                    final V value = Beans.getValue(bean, source);
                    if (value == null) {
                        return defaultValue;
                    }
                    return value;
                }

                @Override
                PAccessor<Field, V> reverse(AccessMode mode) throws BeanException {
                    if (mode == AccessMode.RO) {
                        return this;
                    }
                    return AccessMode.WO.of(AccessType.FIELD, Segment.class, source, null);
                }
            }
        }

        abstract sealed static class PPAccessor<V> extends PAccessor<Field, V> permits PWAccessor, PRAccessor, PNAccessor {
            final Class<? extends Segment> container;
            final Class<V> type;

            private PPAccessor(Class<? extends Segment> container, Class<V> type, Field source) {
                super(source);
                this.type = type;
                this.container = container;
            }

            static final class PRAccessor<V> extends PPAccessor<V> {
                private final Method getter;

                PRAccessor(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
                    super(container, type, field);
                    this.getter = getAccessibleGetter(container, field, type);
                }

                @Override
                V value(Segment bean, V defaultValue) {
                    final V value = Beans.getValue(bean, getter);
                    if (value == null) {
                        return defaultValue;
                    }
                    return value;
                }

                @Override
                PAccessor<Field, V> reverse(AccessMode mode) throws BeanException {
                    if (mode == AccessMode.RO) {
                        return this;
                    }
                    return AccessMode.WO.of(PROPERTY, container, source, type);
                }
            }

            static final class PWAccessor<V> extends PPAccessor<V> {
                private final Method setter;

                PWAccessor(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
                    super(container, type, field);
                    this.setter = getAccessibleSetter(container, field, type);
                }

                @Override
                V value(Segment bean, V value) {
                    Beans.setValue(bean, value, setter);
                    return value;
                }

                @Override
                PPAccessor<V> reverse(AccessMode mode) throws BeanException {
                    if (mode == AccessMode.WO) {
                        return this;
                    }
                    return new PRAccessor<>(container, type, source);
                }
            }

            static final class PNAccessor<V> extends PPAccessor<V> {

                PNAccessor(Class<? extends Segment> container, Class<V> type, Field field) {
                    super(container, type, field);
                }

                @Override
                V value(Segment bean, V value) {
                    throw new AccessException(PNAccessor.class, "value", "unsupported operation");
                }

                @Override
                PPAccessor<V> reverse(AccessMode mode) throws BeanException {
                    if (mode == AccessMode.WO) {
                        return this;
                    }
                    return new PRAccessor<>(container, type, source);
                }
            }
        }

    }
}
