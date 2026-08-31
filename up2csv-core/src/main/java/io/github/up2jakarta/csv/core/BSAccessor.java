package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.csv.ext.Beans;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.persistence.AccessType;
import jakarta.validation.Validator;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.BSBuilder.MST;
import static io.github.up2jakarta.csv.core.BeanAccessor.*;

/**
 * Internal property accessor.
 */
abstract sealed class BSAccessor<V> implements MST permits BSAccessor.FA, BSAccessor.OA, BSAccessor.RA {
    protected final Field source;

    private BSAccessor(Field source) {
        this.source = source;
    }

    static ILinker<Segment, Segment> getLinker(Field fp, Class<? extends Segment> pc) throws BeanException {
        if (fp.getType().equals(Wrapper.class)) {
            return BeanChecker.check(new ALinker.WL(fp), pc, fp, "new Wrapper<>()");
        } else if (fp.getType().equals(Optional.class)) {
            if (Modifier.isFinal(fp.getModifiers())) {
                throw new BeanException(fp, "should not be final or replace Optional with Wrapper");
            }
            return BeanChecker.check(new ALinker.OL(fp), pc, fp, "Optional.empty()");
        }
        return new ALinker.SL(fp);
    }

    V value(Segment bean) {
        throw new AccessException(bean.getClass(), source.getName(), "unsupported read operation");
    }

    void value(Segment bean, V value) {
        throw new AccessException(bean.getClass(), source.getName(), "unsupported write operation");
    }

    abstract boolean isReversible();

    abstract BSAccessor<V> reverse(Mode mode) throws BeanException;

    /**
     * Final Accessor
     */
    abstract static sealed class FA<V> extends BSAccessor<V> permits DRW, FRO, FRW, WRO {
        private FA(Field source) {
            super(source);
        }

        @Override
        final boolean isReversible() {
            return false;
        }

        @Override
        final FA<V> reverse(Mode mode) {
            return this;
        }
    }

    /**
     * Final RO Accessor
     */
    private static final class FRO<V> extends FA<V> {
        private final IGetter<V> getter;

        private FRO(Field field, IGetter<V> getter) {
            super(field);
            this.getter = getter;
        }

        @Override
        V value(Segment bean) {
            return getter.get(bean, source);
        }
    }

    /**
     * Wrapper RO Accessor
     */
    private static final class WRO<V> extends FA<V> {
        private final IGetter<Wrapper<V>> getter;

        private WRO(Field field, IGetter<Wrapper<V>> getter) {
            super(field);
            this.getter = getter;
        }

        @Override
        V value(Segment bean) {
            return getter.get(bean, source).get();
        }

        @Override
        void value(Segment bean, V value) {
            getter.get(bean, source).accept(value);
        }
    }

    /**
     * Delegate RW Accessor
     */
    private static final class DRW<V> extends FA<V> {
        private final IAccessor<V> delegate;

        private DRW(Field field, IAccessor<V> delegate) {
            super(field);
            this.delegate = delegate;
        }

        @Override
        V value(Segment bean) {
            return delegate.get(bean, source);
        }

        @Override
        void value(Segment bean, V value) {
            delegate.set(bean, value, source);
        }
    }

    /**
     * Final RW Accessor
     */
    private static final class FRW<V> extends FA<V> {
        private final IGetter<V> getter;
        private final ISetter<V> setter;

        private FRW(Field field, IGetter<V> getter, ISetter<V> setter) {
            super(field);
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        V value(Segment bean) {
            return getter.get(bean, source);
        }

        @Override
        void value(Segment bean, V value) {
            setter.set(bean, value, source);
        }
    }

    /**
     * Reversible Accessor
     */
    abstract static sealed class RA<V> extends BSAccessor<V> permits RRO, FWO, RWO {
        protected final Class<? extends Segment> container;
        protected final Class<V> type;

        private RA(Class<? extends Segment> container, Class<V> type, Field source) {
            super(source);
            this.type = type;
            this.container = container;
        }

        @Override
        final boolean isReversible() {
            return true;
        }
    }

    /**
     * Reversible RO Accessor
     */
    private static final class RRO<V> extends RA<V> {
        private final IGetter<V> getter;

        private RRO(Class<? extends Segment> container, Class<V> type, Field field, IGetter<V> getter) {
            super(container, type, field);
            this.getter = getter;
        }

        @Override
        V value(Segment bean) {
            return getter.get(bean, source);
        }

        @Override
        BSAccessor<V> reverse(Mode mode) throws BeanException {
            if (mode == Mode.RO) {
                return this;
            }
            return new FRW<>(source, getter, Mode.findSetter(container, source, type));
        }
    }

    /**
     * Reversible WO Accessor
     */
    private static final class RWO<V> extends RA<V> {
        private final ISetter<V> setter;

        private RWO(Class<? extends Segment> container, Class<V> type, Field field, ISetter<V> setter) {
            super(container, type, field);
            this.setter = setter;
        }

        @Override
        void value(Segment bean, V value) {
            setter.set(bean, value, source);
        }

        @Override
        BSAccessor<V> reverse(Mode mode) throws BeanException {
            if (mode == Mode.WO) {
                return this;
            }
            return new FRW<>(source, Mode.findGetter(container, source, type), setter);
        }
    }

    /**
     * Reversible WO Accessor (Final Property)
     */
    private static final class FWO<V> extends RA<V> {
        private FWO(Class<? extends Segment> container, Class<V> type, Field field) {
            super(container, type, field);
        }

        @Override
        BSAccessor<V> reverse(Mode mode) throws BeanException {
            if (mode == Mode.WO) {
                return this;
            }
            return new FRO<>(source, Mode.findGetter(container, source, type));
        }
    }

    /**
     * {@link Optional} Accessor
     */
    static final class OA<V> extends BSAccessor<V> {
        private final BSAccessor<Optional<V>> delegate;

        private OA(BSAccessor<Optional<V>> delegate) {
            super(delegate.source);
            this.delegate = delegate;
        }

        @Override
        V value(Segment bean) {
            return delegate.value(bean).orElse(null);
        }

        @Override
        void value(Segment bean, V value) {
            delegate.value(bean, Optional.of(value));
        }

        @Override
        boolean isReversible() {
            return delegate.isReversible();
        }

        @Override
        BSAccessor<V> reverse(Mode mode) throws BeanException {
            final BSAccessor<Optional<V>> reverse = delegate.reverse(mode);
            if (reverse != delegate) {
                return new OA<>(reverse);
            }
            return this;
        }
    }

    /**
     * {@link BSNode.Bean} Input
     */
    static abstract sealed class Input permits Input.BI, Input.UInput {
        final int offset;
        final Validator validator;
        private final String[] data;

        private Input(Validator validator, int offset, String[] data) {
            this.validator = validator;
            this.offset = offset;
            this.data = data;
        }

        static Input of(Validator validator, BSLink<?, ?, ?> link, String[] data) {
            if (link.index == 0) {
                return new UInput(validator, link.offset, data);
            }
            return new BI(validator, link.offset, link.index, data);
        }

        static UInput of(Validator validator, int offset, String[] data) {
            return new UInput(validator, offset, data);
        }

        abstract boolean no(int index);

        abstract String at(int index);

        private static final class BI extends Input {
            private final int index, length;

            private BI(Validator validator, int offset, int index, String[] data) {
                super(validator, offset + index, data);
                this.length = data.length - index;
                this.index = index;
            }

            @Override
            boolean no(int index) {
                return index >= length;
            }

            @Override
            String at(int index) {
                return (index < length) ? super.data[index + this.index] : null;
            }
        }

        private static final class UInput extends Input {
            private UInput(Validator validator, int offset, String[] data) {
                super(validator, offset, data);
            }

            @Override
            boolean no(int index) {
                return index >= super.data.length;
            }

            @Override
            String at(int index) {
                return (index < super.data.length) ? super.data[index] : null;
            }
        }
    }

    /**
     * Internal Automatic Linker
     */
    private static abstract sealed class ALinker implements ILinker<Segment, Segment> permits ALinker.OL, ALinker.SL, ALinker.WL {
        protected final Member source;

        private ALinker(Member source) {
            this.source = source;
        }

        private static final class SL extends ALinker {
            private final BeanAccessor.IAccessor<Segment> access;

            private SL(Field source) {
                super(source);
                this.access = getInstance().toAccessor(source);
            }

            @Override
            public Collection<Segment> from(Segment parent) {
                return of(access.get(parent, source));
            }

            @Override
            public void link(Segment parent, Segment child) {
                access.set(parent, child, source);
            }
        }

        private static final class WL extends ALinker {
            private final IGetter<Wrapper<Segment>> access;

            private WL(Field source) {
                super(source);
                this.access = getInstance().toGetter(source);
            }

            @Override
            public Collection<Segment> from(Segment parent) {
                return of(access.get(parent, source).get());
            }

            @Override
            public void link(Segment parent, Segment child) {
                access.get(parent, source).accept(child);
            }
        }

        private static final class OL extends ALinker {
            private final IAccessor<Optional<Segment>> access;

            private OL(Field source) {
                super(source);
                this.access = getInstance().toAccessor(source);
            }

            @Override
            public Collection<Segment> from(Segment parent) {
                return of(access.get(parent, source).orElse(null));
            }

            @Override
            public void link(Segment parent, Segment child) {
                access.set(parent, Optional.of(child), source);
            }
        }
    }

    /**
     * Access mode of properties.
     */
    abstract static class Mode {
        /**
         * Read only access.
         */
        static final Mode RO = new Mode() {
            @Override
            <V> BSAccessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
                if (fp.getType() == Wrapper.class) {
                    return Mode.findAccessor(at, st, fp, ft);
                }
                final boolean ff = Modifier.isFinal(fp.getModifiers());
                if (AccessType.FIELD == at) {
                    return Mode.findAccessor(fp, ff);
                }
                final IGetter<V> mh = Mode.findGetter(st, fp, ft);
                return (ff) ? new FRO<>(fp, mh) : new RRO<>(st, ft, fp, mh);
            }
        };
        /**
         * Write only access.
         */
        static final Mode WO = new Mode() {
            @Override
            <V> BSAccessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
                if (fp.getType() == Wrapper.class) {
                    return Mode.findAccessor(at, st, fp, ft);
                }
                final boolean ff = Modifier.isFinal(fp.getModifiers());
                if (AccessType.FIELD == at) {
                    return Mode.findAccessor(fp, ff);
                } else if (ff) {
                    return new FWO<>(st, ft, fp);
                } else {
                    return new RWO<>(st, ft, fp, findSetter(st, fp, ft));
                }
            }
        };

        private Mode() {
        }

        private static <V> BSAccessor<V> findAccessor(AccessType at, Class<?> st, Field fp, Class<?> ft) throws BeanException {
            final IGetter<Wrapper<V>> getter;
            if (at == AccessType.FIELD) {
                getter = getInstance().toGetter(fp);
            } else {
                getter = findGetter(st, fp, ft);
            }
            return new WRO<>(fp, getter);
        }

        private static <V> BSAccessor<V> findAccessor(Field fp, boolean ff) {
            if (ff) {
                return new FRO<>(fp, getInstance().toGetter(fp));
            }
            return new DRW<>(fp, getInstance().toAccessor(fp));
        }

        private static <V> IGetter<V> findGetter(Class<?> st, Field fp, Class<?> ft) throws BeanException {
            final Method getter = Beans.findGetter(st, fp, ft);
            return getInstance().toGetter(getter);
        }

        private static <V> ISetter<V> findSetter(Class<?> st, Field fp, Class<?> ft) throws BeanException {
            final Method setter = Beans.findSetter(st, fp, ft);
            return getInstance().toSetter(setter);
        }

        @SuppressWarnings("unchecked")
        static <T> BSAccessor<T> wrap(BSAccessor<T> delegate) {
            return new OA<>((BSAccessor<Optional<T>>) delegate);
        }

        static <T extends Segment> ICreator<T> findCreator(Constructor<T> source) throws BeanException {
            return getInstance().toCreator(source);
        }

        abstract <V> BSAccessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException;
    }
}
