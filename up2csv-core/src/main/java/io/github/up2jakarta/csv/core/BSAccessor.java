package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.persistence.AccessType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.core.Beans.getValue;
import static jakarta.persistence.AccessType.FIELD;

/**
 * Internal property accessor.
 */
abstract sealed class BSAccessor<V> implements BSBuilder.MST permits BSAccessor.FA, BSAccessor.PA {
    protected final Field source;

    BSAccessor(Field source) {
        this.source = source;
    }

    final boolean isFinal() {
        if (this instanceof FRO<?> || this instanceof PWC<?>) {
            return true;
        }
        return Modifier.isFinal(source.getModifiers());
    }

    abstract V value(Segment bean) throws AccessException;

    abstract void value(Segment bean, V value) throws AccessException;

    abstract BSAccessor<V> reverse(Mode mode) throws BeanException;

    /**
     * Final Access
     */
    abstract sealed static class FA<V> extends BSAccessor<V> permits FRW, FRO, PRC, PRW {
        private FA(Field source) {
            super(setAccessible(source));
        }

        @Override
        final FA<V> reverse(Mode mode) {
            return this;
        }
    }

    /**
     * Property Access
     */
    abstract sealed static class PA<V> extends BSAccessor<V> permits PWO, PRO, PWC {
        protected final Class<? extends Segment> container;
        protected final Class<V> type;

        private PA(Class<? extends Segment> container, Class<V> type, Field source) {
            super(source);
            this.type = type;
            this.container = container;
        }
    }

    /**
     * Property RO Access
     */
    static final class PRO<V> extends PA<V> {
        private final Method getter;

        PRO(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
            super(container, type, field);
            this.getter = getAccessibleGetter(container, field, type);
        }

        @Override
        V value(Segment bean) {
            return getValue(bean, getter);
        }

        @Override
        void value(Segment bean, V value) {
            throw new AccessException(bean.getClass(), source.getName(), "unsupported write operation");
        }

        @Override
        BSAccessor<V> reverse(Mode mode) throws BeanException {
            if (mode == Mode.RO) {
                return this;
            }
            return new PRW<>(container, type, source);
        }
    }

    /**
     * Property WO Access
     */
    static final class PWO<V> extends PA<V> {
        private final Method setter;

        PWO(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
            super(container, type, field);
            this.setter = getAccessibleSetter(container, field, type);
        }

        @Override
        V value(Segment bean) {
            throw new AccessException(bean.getClass(), source.getName(), "unsupported read operation");
        }

        @Override
        void value(Segment bean, V value) {
            setValue(bean, value, setter);
        }

        @Override
        BSAccessor<V> reverse(Mode mode) throws BeanException {
            if (mode == Mode.WO) {
                return this;
            }
            return new PRW<>(container, type, source);
        }
    }

    /**
     * Final Property RW Access
     */
    static final class PRW<V> extends FA<V> {
        private final Method getter;
        private final Method setter;

        private PRW(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
            super(field);
            this.getter = getAccessibleGetter(container, field, type);
            this.setter = getAccessibleSetter(container, field, type);
        }

        @Override
        V value(Segment bean) {
            return getValue(bean, getter);
        }

        @Override
        void value(Segment bean, V value) {
            setValue(bean, value, setter);
        }
    }

    /**
     * Final Property WO Access
     */
    static final class PWC<V> extends PA<V> {
        PWC(Class<? extends Segment> container, Class<V> type, Field field) {
            super(container, type, field);
        }

        @Override
        V value(Segment bean) {
            throw new AccessException(bean.getClass(), source.getName(), "unsupported read operation");
        }

        @Override
        void value(Segment bean, V value) {
            throw new AccessException(bean.getClass(), source.getName(), "unsupported write operation");
        }

        @Override
        BSAccessor<V> reverse(Mode mode) throws BeanException {
            if (mode == Mode.WO) {
                return this;
            }
            return new PRC<>(container, type, source);
        }
    }

    /**
     * Final Property RO Access
     */
    static final class PRC<V> extends FA<V> {
        private final Method getter;

        PRC(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
            super(field);
            this.getter = getAccessibleGetter(container, field, type);
        }

        @Override
        V value(Segment bean) {
            return getValue(bean, getter);
        }

        @Override
        void value(Segment bean, V value) {
            throw new AccessException(bean.getClass(), source.getName(), "unsupported write operation");
        }
    }

    /**
     * Field RW Access
     */
    static final class FRW<V> extends FA<V> {
        FRW(Field field) {
            super(field);
        }

        @Override
        V value(Segment bean) {
            return getValue(bean, source);
        }

        @Override
        void value(Segment bean, V value) {
            setValue(bean, value, source);
        }
    }

    /**
     * Final Field RO Access
     */
    static final class FRO<V> extends FA<V> {
        FRO(Field field) {
            super(field);
        }

        @Override
        V value(Segment bean) {
            return getValue(bean, source);
        }

        @Override
        void value(Segment bean, V value) {
            throw new AccessException(bean.getClass(), source.getName(), "unsupported write operation");
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
                final boolean ff = Modifier.isFinal(fp.getModifiers());
                if (FIELD == at) {
                    return (ff) ? new FRO<>(fp) : new FRW<>(fp);
                }
                return (ff) ? new PRC<>(st, ft, fp) : new PRO<>(st, ft, fp);
            }
        };
        /**
         * Write only access.
         */
        static final Mode WO = new Mode() {
            @Override
            <V> BSAccessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
                final boolean ff = Modifier.isFinal(fp.getModifiers());
                if (FIELD == at) {
                    return (ff) ? new FRO<>(fp) : new FRW<>(fp);
                }
                return (ff) ? new PWC<>(st, ft, fp) : new PWO<>(st, ft, fp);
            }
        };

        private Mode() {
        }

        abstract <V> BSAccessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException;
    }
}
