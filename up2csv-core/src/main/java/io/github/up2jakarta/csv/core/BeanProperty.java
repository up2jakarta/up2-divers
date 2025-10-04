package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Listable;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Internal Fragment implementation.
 */
final class BeanProperty<S extends Segment, B extends DataType<B>> extends Property<Segment, B> implements Listable<Property<?, B>> {

    final BeanNode<S, B> node;

    BeanProperty(
            Class<S> type, B bType, Field field, int offset, boolean nv, ValidationContext vc, List<Property<?, B>> ps
    ) throws BeanException {
        super(field, bType, offset);
        this.node = BeanNode.node(type, vc, nv, ps);
    }

    @Override
    public List<Property<?, B>> toList() {
        return node.toList();
    }

    @Override
    S defaultValue() throws BeanException {
        return node.newInstance();
    }

}
