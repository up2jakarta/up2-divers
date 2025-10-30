package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.BusinessIdentifier;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.reflect.AnnotatedElement;

/**
 * Internal property representation.
 *
 * @param <V> the value type
 */
public abstract sealed class Property<V, D extends DataType<D>> permits PFProperty, PProperty {

    public final int offset;
    public final D dataType;
    public final Error error;
    protected final V defaultValue;
    private final PAccessor<?, V> accessor;

    private Property(PAccessor<?, V> accessor, D dataType, int offset, DefaultValue<V, D> dvs) throws BeanException {
        this.offset = offset;
        this.dataType = dataType;
        this.accessor = accessor;
        this.error = accessor.source.getAnnotation(Error.class);
        this.defaultValue = dvs.get(this);
    }

    Property(Property<V, D> source, V defaultValue) throws BeanException {
        this.accessor = source.accessor.reverse();
        this.defaultValue = defaultValue;
        this.dataType = source.dataType;
        this.offset = source.offset;
        this.error = source.error;
    }

    Property(PAccessor<?, V> accessor, D dataType, int offset, Fragment fp, V defaultValue) throws BeanException {
        this(accessor, dataType, offset + fp.value(), (p) -> defaultValue);
    }

    Property(PAccessor<?, V> va, D dt, int fo, Position pp, DefaultValue<V, D> dv) throws BeanException {
        this(va, dt, fo + pp.value(), dv);
    }

    public static <S extends Segment> BusinessIdentifier<S> id(Property<?, ?>[] path) throws BeanException {
        if (path.length == 1) {
            final PAccessor<?, ?> getter = path[0].accessor.reverse();
            return b -> getter.value(b, null);
        }
        final PAccessor<?, ?>[] getters = new PAccessor[path.length];
        for (var i = 0; i < path.length; i++) {
            getters[i] = path[i].accessor.reverse();
        }
        return b -> {
            Object bean = b;
            for (final PAccessor<?, ?> getter : getters) {
                bean = getter.value(bean, null);
                if (bean == null) {
                    break;
                }
            }
            return bean;
        };
    }

    public final String getName() {
        return accessor.getName();
    }

    public final Class<V> getType() {
        return accessor.type;
    }

    public final AnnotatedElement getSource() {
        return accessor.source;
    }

    @SuppressWarnings("unchecked")
    public final <T> T get(Segment bean) throws BeanException {
        if (bean == null) {
            return (T) defaultValue;
        }
        return (T) accessor.value(bean, defaultValue);
    }

    public final V set(Object bean, V value) throws BeanException {
        if (value != null) {
            return accessor.value(bean, value);
        }
        return null;
    }

    @Override
    public final String toString() {
        return accessor.getName();
    }

    @FunctionalInterface
    protected interface DefaultValue<T, D extends DataType<D>> {

        T get(Property<T, D> pp) throws BeanException;

    }
}
