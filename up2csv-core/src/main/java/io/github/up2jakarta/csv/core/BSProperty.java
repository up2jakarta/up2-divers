package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.BSBuilder.Input;
import io.github.up2jakarta.csv.core.BSBuilder.MST;
import io.github.up2jakarta.csv.core.BSOperator.BId.DP;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Wrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.BSAccessor.FA;
import static io.github.up2jakarta.csv.core.BSAccessor.Mode;
import static io.github.up2jakarta.csv.core.BSProperty.PFragment.*;
import static io.github.up2jakarta.csv.core.BSProperty.PPosition.*;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

/**
 * Internal property representation.
 */
abstract sealed class BSProperty<T, V, D extends DataType<D>> implements MST permits PFragment, PPosition {
    protected final int offset;
    protected final D dataType;
    protected final Error error;
    private final BSAccessor<V> access;

    private BSProperty(BSAccessor<V> access, D dataType, int offset) {
        this.error = access.source.getAnnotation(Error.class);
        this.dataType = dataType;
        this.access = access;
        this.offset = offset;
    }

    BSProperty(BSProperty<T, ?, D> source, BSAccessor<V> access) {
        this.dataType = source.dataType;
        this.offset = source.offset;
        this.error = source.error;
        this.access = access;
    }

    BSProperty(BSProperty<T, V, D> source) throws BeanException {
        this(source, source.access.reverse(null));
    }

    BSProperty(BSAccessor<V> access, D dataType, int offset, Fragment fp) {
        this(access, dataType, offset + fp.value());
    }

    BSProperty(BSAccessor<V> access, D dt, int fo, Position pp) {
        this(access, dt, fo + pp.value());
    }

    static DP id(Mode mode, PFragment<Segment, ?, ?>[] fs, PPosition<Object, Object, ?> pp) throws BeanException {
        final BSAccessor<Object> accessor = ((BSProperty<Object, Object, ?>) pp).access;
        final List<PFragment<Segment, ?, ?>> path;
        final PPosition<Object, Object, ?> getter;
        final BSAccessor<Object> setter;
        if (mode == Mode.WO) {
            setter = accessor;
            getter = pp.reverse();
            if (fs.length != 0) {
                List<PFragment<Segment, ?, ?>> ps = new ArrayList<>(fs.length);
                for (final PFragment<Segment, ?, ?> fp : fs) {
                    ps.add(fp.reverse());
                }
                path = unmodifiableList(ps);
            } else {
                path = List.of();
            }
        } else {
            getter = pp;
            path = asList(fs);
            if (pp.isReversible()) {
                setter = accessor.reverse(Mode.WO);
            } else {
                setter = accessor;
            }
        }
        return DP.of(path, getter, setter);
    }

    abstract Class<T> getType();

    abstract V wrap(T value);

    abstract T from(V value);

    abstract BSProperty<T, V, D> reverse() throws BeanException;

    <A extends Annotation> A getAnnotation(Class<A> type) {
        return access.source.getAnnotation(type);
    }

    final boolean isReversible() {
        return !(access instanceof FA<V>);
    }

    final Type getGenericType() {
        return access.source.getGenericType();
    }

    final Member getSource() {
        return access.source;
    }

    final boolean isFinal() {
        return access.isFinal();
    }

    final String getName() {
        return access.source.getName();
    }

    final T value(Segment bean) {
        final V wrapped = access.value(bean);
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
    abstract static sealed class PFragment<S extends Segment, V, B extends DataType<B>> extends BSProperty<S, V, B> permits FS, FO, FW {
        protected final BSNode<S, B> node;

        private PFragment(BSNode<S, B> node, BSAccessor<V> va, B type, int offset, Fragment pf) {
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
            FS(BSNode<S, B> node, BSAccessor<S> va, B type, int offset, Fragment pf) {
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
            FO(BSNode<S, B> n, BSAccessor<Optional<S>> a, B pt, int po, Fragment pf) {
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
            FW(BSNode<S, B> n, BSAccessor<Wrapper<S>> a, B pt, int po, Fragment pf) {
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
    abstract static sealed class PPosition<T, V, D extends DataType<D>> extends BSProperty<T, V, D> permits PS, PO, PW {
        protected final Class<T> type;
        protected final T defaultValue;
        protected final boolean required;
        protected final String formatted;
        private final Input<D> processor;
        private final TypeAdapter<T> adapter;

        private PPosition(BSAccessor<V> va, D dt, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
            super(va, dt, fo, pp);
            this.defaultValue = ps.parse(this, pa);
            this.formatted = ps.format(this.getSource(), defaultValue, pa);
            this.required = pp.required();
            this.type = pa.getType();
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
                    handler.handle(this.dataType, offset + this.offset, cause, this.error);
                }
            }
            return null;
        }

        final String toString(Segment bean) {
            final T value = this.value(bean);
            return this.format(value);
        }

        final T setValue(BSAccessor<V> setter, Segment bean, T value) {
            final V wrapped = this.wrap(value);
            if (wrapped != null) {
                setter.value(bean, wrapped);
            }
            return value;
        }

        @Override
        final PPosition<T, V, D> reverse() throws BeanException {
            if (this.isReversible()) {
                return this.doReverse();
            }
            return this;
        }

        abstract PPosition<T, V, D> doReverse() throws BeanException;

        /**
         * Internal representation for {@link Object} property.
         */
        static final class PS<T, D extends DataType<D>> extends PPosition<T, T, D> {
            PS(BSAccessor<T> va, D type, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
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
            PS<T, D> doReverse() throws BeanException {
                return new PS<>(this);
            }
        }

        /**
         * Internal representation for {@link Optional} property.
         */
        static final class PO<T, D extends DataType<D>> extends PPosition<T, Optional<T>, D> {
            private final Optional<T> wrapped;

            PO(BSAccessor<Optional<T>> va, D type, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
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
            PO<T, D> doReverse() throws BeanException {
                return new PO<>(this);
            }
        }

        /**
         * Internal representation for {@link Optional} property.
         */
        static final class PW<T, D extends DataType<D>> extends PPosition<T, Wrapper<T>, D> {
            PW(BSAccessor<Wrapper<T>> va, D type, int fo, Position pp, Input<D> ps, TypeAdapter<T> pa) throws BeanException {
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
            PW<T, D> doReverse() throws BeanException {
                return new PW<>(this);
            }
        }
    }

}
