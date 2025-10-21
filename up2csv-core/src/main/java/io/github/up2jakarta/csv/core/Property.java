package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.data.BusinessIdentifier;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Internal property representation.
 *
 * @param <V> the value type
 */
abstract class Property<V, D extends DataType<D>> {

    protected final int offset;
    protected final D dataType;
    protected final Error error;
    private final Accessor<V> accessor;

    Property(Accessor<V> accessor, D dataType, int offset) {
        this.dataType = dataType;
        this.accessor = accessor;
        this.offset = offset;
        this.error = accessor.field.getAnnotation(Error.class);
    }

    Property(Property<V, D> source) throws BeanException {
        this.dataType = source.dataType;
        this.accessor = source.accessor.reverse();
        this.offset = source.offset;
        this.error = source.error;
    }

    static <S extends Segment> BusinessIdentifier<S> id(Property<?, ?>[] path) throws BeanException {
        if (path.length == 1) {
            final Property.Accessor<?> getter = path[0].accessor.forRead();
            return b -> getter.value(b, null);
        }
        final Property.Accessor<?>[] getters = new Property.Accessor[path.length];
        for (var i = 0; i < path.length; i++) {
            getters[i] = path[i].accessor.forRead();
        }
        return b -> {
            Object bean = b;
            for (final Property.Accessor<?> getter : getters) {
                bean = getter.value(bean, null);
                if (bean == null) {
                    break;
                }
            }
            return bean;
        };
    }

    String getName() {
        return accessor.field.getName();
    }

    Class<V> getType() {
        return accessor.type;
    }

    Field getField() {
        return accessor.field;
    }

    final V getValue(Object bean) throws BeanException {
        if (bean == null) {
            return this.defaultValue();
        }
        return accessor.value(bean, this.defaultValue());
    }

    final V setValue(Object bean, V value) throws BeanException {
        if (value != null) {
            return accessor.value(bean, value);
        }
        return null;
    }

    abstract V defaultValue();

    @Override
    public String toString() {
        return accessor.field.getName();
    }

    abstract static class Accessor<V> {
        private final Field field;
        private final Class<V> type;

        Accessor(Class<V> type, Field field) {
            this.type = type;
            this.field = field;
        }

        static <V> Accessor<V> of(boolean read, Class<V> type, Field field) throws BeanException {
            if (read) {
                return new ROAccess<>(type, field);
            }
            return new WOAccess<>(type, field);
        }

        private Accessor<V> forRead() throws BeanException {
            if (this instanceof ROAccess<?>) {
                return this;
            }
            return new ROAccess<>(type, field);
        }

        private Accessor<V> reverse() throws BeanException {
            if (this instanceof ROAccess<?>) {
                return new WOAccess<>(type, field);
            }
            return new ROAccess<>(type, field);
        }

        abstract V value(Object bean, V defaultValue) throws BeanException;
    }

    static final class ROAccess<V> extends Accessor<V> {
        private final Method getter;

        private ROAccess(Class<V> type, Field field) throws BeanException {
            super(type, field);
            this.getter = Beans.getAccessibleGetter(field);
        }

        @Override
        V value(Object bean, V defaultValue) throws BeanException {
            //noinspection unchecked
            final V value = (V) Beans.getValue(bean, getter);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }
    }

    static final class WOAccess<V> extends Accessor<V> {
        private final Method setter;

        private WOAccess(Class<V> type, Field field) throws BeanException {
            super(type, field);
            if (Modifier.isFinal(field.getModifiers())) {
                throw new BeanException(field, "must not be final");
            }
            this.setter = Beans.getAccessibleSetter(field);
        }

        @Override
        V value(Object bean, V value) throws BeanException {
            Beans.setValue(bean, value, setter);
            return value;
        }
    }

}
