package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.BSBuilder.Input;
import io.github.up2jakarta.csv.core.BSBuilder.MST;
import io.github.up2jakarta.csv.core.BSOperator.BId.DP;
import io.github.up2jakarta.csv.core.BSProperty.Accessor.PN;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.persistence.AccessType;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.BSProperty.PFragment;
import static io.github.up2jakarta.csv.core.BSProperty.PFragment.*;
import static io.github.up2jakarta.csv.core.BSProperty.PPosition;
import static io.github.up2jakarta.csv.core.BSProperty.PPosition.*;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static jakarta.persistence.AccessType.PROPERTY;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * Internal property representation.
 */
abstract sealed class BSProperty<T, V, D extends DataType<D>> implements MST permits PFragment, PPosition {
    protected final int offset;
    protected final D dataType;
    protected final Error error;
    private final Accessor<V> access;

    private BSProperty(Accessor<V> access, D dataType, int offset) {
        this.error = access.source.getAnnotation(Error.class);
        this.access = requireNonNull(access);
        this.offset = offset;
        this.dataType = dataType;
    }

    BSProperty(BSProperty<T, ?, D> source, Accessor<V> access) {
        this.dataType = source.dataType;
        this.offset = source.offset;
        this.error = source.error;
        this.access = access;
    }

    BSProperty(BSProperty<T, V, D> source) throws BeanException {
        this(source, source.access.reverse(null));
    }

    BSProperty(Accessor<V> access, D dataType, int offset, Fragment fp) throws BeanException {
        this(access, dataType, offset + fp.value());
    }

    BSProperty(Accessor<V> access, D dt, int fo, Position pp) throws BeanException {
        this(access, dt, fo + pp.value());
    }

    static DP id(List<? extends PFragment<Segment, ?, ?>> fs, PPosition<Object, Object, ?> pp) throws BeanException {
        final Accessor<Object> getter = ((BSProperty<Object, Object, ?>) pp).access;
        if (getter.isFinal()) {
            return DP.of(fs, pp, new PN<>(Segment.class, pp.type, (Field) pp.getSource()));
        }
        return DP.of(fs, pp, getter.reverse(BeanAccess.WO));
    }

    static boolean isFinal(BSProperty<?, ?, ?> p) {
        return p.access.isFinal();
    }

    abstract Class<T> getType();

    abstract V wrap(T value);

    abstract T from(V value);

    final String getName() {
        return access.source.getName();
    }

    abstract BSProperty<T, V, D> reverse() throws BeanException;

    public <A extends Annotation> A getAnnotation(Class<A> type) {
        return access.source.getAnnotation(type);
    }

    final Member getSource() {
        return access.source;
    }

    final Type getGenericType() {
        return access.source.getGenericType();
    }

    final T value(Segment bean) {
        final V wrapped = access.value(bean, null);
        if (wrapped != null) {
            return this.from(wrapped);
        }
        return null;
    }

    final void value(Segment bean, V value) {
        if (value != null) {
            access.value(bean, value);
        }
    }

    @Override
    public final String toString() {
        return this.getName();
    }

    /**
     * Internal {@link Fragment} implementation.
     */
    abstract sealed static class PFragment<S extends Segment, V, B extends DataType<B>> extends BSProperty<S, V, B> permits FS, FO, FW {
        protected final BSNode<S, B> node;

        private PFragment(BSNode<S, B> node, Accessor<V> va, B type, int offset, Fragment pf) throws BeanException {
            super(va, type, offset, pf);
            this.node = node;
        }

        private PFragment(BSNode<S, B> node, PFragment<S, V, B> source) throws BeanException {
            super(source);
            this.node = node;
        }

        @Override
        final Class<S> getType() {
            return node.type;
        }

        @Override
        abstract PFragment<S, V, B> reverse() throws BeanException;

        /**
         * Internal representation for {@link Segment} fragment.
         */
        static final class FS<S extends Segment, B extends DataType<B>> extends PFragment<S, S, B> {
            FS(BSNode<S, B> node, Accessor<S> va, B type, int offset, Fragment pf) throws BeanException {
                super(node, va, type, offset, pf);
            }

            private FS(BSNode<S, B> node, PFragment<S, S, B> source) throws BeanException {
                super(node, source);
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
            FS<S, B> reverse() throws BeanException {
                return new FS<>(node.reverse(), this);
            }
        }

        /**
         * Internal representation for {@link Optional} fragment.
         */
        static final class FO<S extends Segment, B extends DataType<B>> extends PFragment<S, Optional<S>, B> {
            FO(BSNode<S, B> n, Accessor<Optional<S>> a, B pt, int po, Fragment pf) throws BeanException {
                super(n, a, pt, po, pf);
            }

            private FO(BSNode<S, B> node, FO<S, B> source) throws BeanException {
                super(node, source);
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
            FO<S, B> reverse() throws BeanException {
                return new FO<>(node.reverse(), this);
            }
        }

        /**
         * Internal representation for {@link Wrapper} fragment.
         */
        static final class FW<S extends Segment, B extends DataType<B>> extends PFragment<S, Wrapper<S>, B> {
            FW(BSNode<S, B> n, Accessor<Wrapper<S>> a, B pt, int po, Fragment pf) throws BeanException {
                super(n, a, pt, po, pf);
            }

            private FW(BSNode<S, B> node, FW<S, B> source) throws BeanException {
                super(node, source);
            }

            @Override
            Wrapper<S> wrap(S value) {
                return new Wrapper<>(value);
            }

            @Override
            S from(Wrapper<S> value) {
                return value.get();
            }

            @Override
            FW<S, B> reverse() throws BeanException {
                return new FW<>(node.reverse(), this);
            }
        }
    }

    /**
     * Internal {@link Position} implementation.
     */
    abstract sealed static class PPosition<T, V, D extends DataType<D>> extends BSProperty<T, V, D> permits PS, PO, PW {
        protected final Class<T> type;
        protected final T defaultValue;
        protected final boolean required;
        protected final String formatted;
        private final Input<D> processor;
        private final TypeAdapter<T> adapter;

        private PPosition(Accessor<V> va, D dt, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
            super(va, dt, fo, pp);
            this.defaultValue = ps.parse(this, pa);
            this.formatted = ps.format(this.getSource(), defaultValue, pa);
            this.type = pa.getSupportedType();
            this.required = pp.required();
            this.processor = ps;
            this.adapter = pa;
        }

        private PPosition(PPosition<T, V, D> source, T defaultValue) throws BeanException {
            super(source);
            this.processor = source.processor;
            this.formatted = source.formatted;
            this.defaultValue = defaultValue;
            this.required = source.required;
            this.adapter = source.adapter;
            this.type = source.type;
        }

        @Override
        final Class<T> getType() {
            return type;
        }

        final String format(T value) {
            if (value != defaultValue && value != null) {
                return adapter.format(value);
            }
            return formatted;
        }

        final T parse(String data, int offset, EventHandler<D> handler) {
            if (data != null) {
                data = processor.process(data, offset, this, handler);
            } else {
                return defaultValue;
            }
            if (data != null) {
                try {
                    return adapter.parse(data);
                } catch (RuntimeException cause) {
                    handler.handle(dataType, offset + this.offset, cause, this.error);
                }
            }
            return null;
        }

        final String toString(Segment bean) {
            final T value = this.value(bean);
            return this.format(value);
        }

        final T setValue(Accessor<V> setter, Segment bean, T value) {
            final V wrapped = this.wrap(value);
            if (wrapped != null) {
                setter.value(bean, wrapped);
            }
            return value;
        }

        @Override
        abstract PPosition<T, V, D> reverse() throws BeanException;

        /**
         * Internal representation for {@link Object} property.
         */
        static final class PS<T, D extends DataType<D>> extends PPosition<T, T, D> {
            PS(Accessor<T> va, D type, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
                super(va, type, fo, pp, ps, pa);
            }

            private PS(PS<T, D> source) throws BeanException {
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
            PS<T, D> reverse() throws BeanException {
                return new PS<>(this);
            }
        }

        /**
         * Internal representation for {@link Optional} property.
         */
        static final class PO<T, D extends DataType<D>> extends PPosition<T, Optional<T>, D> {
            private final Optional<T> wrapped;

            PO(Accessor<Optional<T>> va, D type, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
                super(va, type, fo, pp, ps, pa);
                this.wrapped = Optional.ofNullable(defaultValue);
            }

            private PO(PO<T, D> source) throws BeanException {
                super(source, source.defaultValue);
                this.wrapped = source.wrapped;
            }

            @Override
            Optional<T> wrap(T value) {
                if (value == defaultValue) {
                    return wrapped;
                }
                return Optional.ofNullable(value);
            }

            @Override
            T from(Optional<T> value) {
                return value.orElse(null);
            }

            @Override
            PO<T, D> reverse() throws BeanException {
                return new PO<>(this);
            }
        }

        /**
         * Internal representation for {@link Optional} property.
         */
        static final class PW<T, D extends DataType<D>> extends PPosition<T, Wrapper<T>, D> {
            PW(Accessor<Wrapper<T>> va, D type, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
                super(va, type, fo, pp, ps, pa);
            }

            private PW(PW<T, D> source) throws BeanException {
                super(source, source.defaultValue);
            }

            @Override
            Wrapper<T> wrap(T value) {
                return new Wrapper<>(value);
            }

            @Override
            T from(Wrapper<T> value) {
                return value.get();
            }

            @Override
            PW<T, D> reverse() throws BeanException {
                return new PW<>(this);
            }
        }
    }

    /**
     * Internal value Accessor.
     */
    abstract sealed static class Accessor<V> implements MST permits Accessor.FA, Accessor.PA {
        protected final Field source;

        Accessor(Field source) {
            this.source = requireNonNull(source);
        }

        final boolean isFinal() {
            if (this instanceof Accessor.FN<?> || this instanceof Accessor.PN<?>) {
                return true;
            }
            return Modifier.isFinal(source.getModifiers());
        }

        abstract V value(Segment bean, V value) throws AccessException;

        abstract Accessor<V> reverse(BeanAccess mode) throws BeanException;

        private abstract sealed static class FA<V> extends Accessor<V> permits FW, FR, FN {
            private FA(Field source) {
                super(setAccessible(source));
            }
        }

        private abstract sealed static class PA<V> extends Accessor<V> permits PW, PR, PN {
            protected final Class<? extends Segment> container;
            protected final Class<V> type;

            private PA(Class<? extends Segment> container, Class<V> type, Field source) {
                super(source);
                this.type = type;
                this.container = container;
            }
        }

        static final class PR<V> extends PA<V> {
            private final Method getter;

            PR(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
                super(container, type, field);
                this.getter = getAccessibleGetter(container, field, type);
            }

            @Override
            V value(Segment bean, V ignore) {
                return getValue(bean, getter);
            }

            @Override
            Accessor<V> reverse(BeanAccess mode) throws BeanException {
                if (mode == BeanAccess.RO) {
                    return this;
                }
                return BeanAccess.WO.of(PROPERTY, container, source, type);
            }
        }

        static final class PW<V> extends PA<V> {
            private final Method setter;

            PW(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
                super(container, type, field);
                this.setter = getAccessibleSetter(container, field, type);
            }

            @Override
            V value(Segment bean, V value) {
                setValue(bean, value, setter);
                return value;
            }

            @Override
            PA<V> reverse(BeanAccess mode) throws BeanException {
                if (mode == BeanAccess.WO) {
                    return this;
                }
                return new PR<>(container, type, source);
            }
        }

        static final class PN<V> extends PA<V> {
            PN(Class<? extends Segment> container, Class<V> type, Field field) {
                super(container, type, field);
            }

            @Override
            V value(Segment bean, V value) {
                throw new AccessException(bean.getClass(), source.getName(), "unsupported write operation");
            }

            @Override
            PA<V> reverse(BeanAccess mode) throws BeanException {
                if (mode == BeanAccess.WO) {
                    return this;
                }
                return new PR<>(container, type, source);
            }
        }

        static final class FW<V> extends FA<V> {
            FW(Field field) {
                super(field);
            }

            @Override
            V value(Segment bean, V value) {
                setValue(bean, value, source);
                return value;
            }

            @Override
            FA<V> reverse(BeanAccess mode) {
                if (mode == BeanAccess.WO) {
                    return this;
                }
                return new FR<>(source);
            }
        }

        static final class FR<V> extends FA<V> {
            FR(Field field) {
                super(field);
            }

            @Override
            V value(Segment bean, V ignore) {
                return getValue(bean, source);
            }

            @Override
            Accessor<V> reverse(BeanAccess mode) throws BeanException {
                if (mode == BeanAccess.RO) {
                    return this;
                }
                return BeanAccess.WO.of(AccessType.FIELD, Segment.class, source, null);
            }
        }

        static final class FN<V> extends FA<V> {
            FN(Field field) {
                super(field);
            }

            @Override
            V value(Segment bean, V value) {
                throw new AccessException(bean.getClass(), source.getName(), "unsupported write operation");
            }

            @Override
            FA<V> reverse(BeanAccess mode) {
                if (mode == BeanAccess.WO) {
                    return this;
                }
                return new FR<>(source);
            }
        }
    }
}
