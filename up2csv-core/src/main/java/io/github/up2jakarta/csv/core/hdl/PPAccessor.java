package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static jakarta.persistence.AccessType.PROPERTY;

abstract sealed class PPAccessor<V> extends PAccessor<Field, V> permits PPAccessor.WOAccess, PPAccessor.ROAccess, PPAccessor.NOAccess {
    protected final Class<? extends Segment> container;

    private PPAccessor(Class<? extends Segment> container, Class<V> type, Field source) {
        super(type, source);
        this.container = container;
    }

    @Override
    public final String getName() {
        return source.getName();
    }

    static final class ROAccess<V> extends PPAccessor<V> {
        private final Method getter;

        ROAccess(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
            super(container, type, field);
            this.getter = Beans.getAccessibleGetter(container, field, type);
        }

        @Override
        public V value(Object bean, V defaultValue) throws BeanException {
            final V value = Beans.getValue(bean, getter);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }

        @Override
        public PAccessor<Field, V> reverse() throws BeanException {
            return PAMode.WO.of(PROPERTY, container, source, type);
        }
    }

    static final class WOAccess<V> extends PPAccessor<V> {
        private final Method setter;

        WOAccess(Class<? extends Segment> container, Class<V> type, Field field) throws BeanException {
            super(container, type, field);
            this.setter = Beans.getAccessibleSetter(container, field, type);
        }

        @Override
        public V value(Object bean, V value) throws BeanException {
            Beans.setValue(bean, value, setter);
            return value;
        }

        @Override
        public ROAccess<V> reverse() throws BeanException {
            return new ROAccess<>(container, type, source);
        }
    }

    static final class NOAccess<V> extends PPAccessor<V> {

        NOAccess(Class<? extends Segment> container, Class<V> type, Field field) {
            super(container, type, field);
        }

        @Override
        public V value(Object bean, V value) throws BeanException {
            throw BeanException.of(source, "unsupported setter");
        }

        @Override
        public ROAccess<V> reverse() throws BeanException {
            return new ROAccess<>(container, type, source);
        }
    }
}
