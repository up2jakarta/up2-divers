package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ext.Beans;

import java.lang.reflect.Field;

abstract sealed class PFAccessor<V> extends PAccessor<Field, V> permits PFAccessor.WOAccess, PFAccessor.ROAccess {
    private PFAccessor(Class<V> type, Field source) {
        super(type, source);
        Beans.setAccessible(source);
    }

    @Override
    public final String getName() {
        return source.getName();
    }

    static final class WOAccess<V> extends PFAccessor<V> {
        WOAccess(Class<V> type, Field field) {
            super(type, field);
        }

        @Override
        public V value(Object bean, V value) throws BeanException {
            Beans.setValue(bean, value, source);
            return value;
        }

        @Override
        public ROAccess<V> reverse() {
            return new ROAccess<>(type, source);
        }
    }

    static final class ROAccess<V> extends PFAccessor<V> {
        ROAccess(Class<V> type, Field field) {
            super(type, field);
        }

        @Override
        public V value(Object bean, V defaultValue) throws BeanException {
            final V value = Beans.getValue(bean, source);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }

        @Override
        public WOAccess<V> reverse() {
            return new WOAccess<>(type, source);
        }
    }

}
