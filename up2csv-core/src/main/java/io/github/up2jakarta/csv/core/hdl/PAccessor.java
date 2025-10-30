package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BeanException;

import java.lang.reflect.AnnotatedElement;

/**
 * Internal property Accessor.
 */
public abstract sealed class PAccessor<S extends AnnotatedElement, V> permits PFAccessor, PPAccessor {

    final S source;
    final Class<V> type;

    PAccessor(Class<V> type, S source) {
        this.source = source;
        this.type = type;
    }

    abstract V value(Object bean, V value) throws BeanException;

    abstract PAccessor<?, V> reverse() throws BeanException;

    abstract String getName();

}
