package io.github.up2jakarta.csv.core;


import io.github.up2jakarta.csv.core.BeanValidator.Node;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.List;

/**
 * Internal {@link io.github.up2jakarta.csv.cfg.Fragment} implementation.
 */
final class PFProperty<S extends Segment, B extends DataType<B>> extends Property<S, B> {

    final Node<S, B> node;

    PFProperty(Node<S, B> node, Accessor<S> va, B type, int offset) {
        super(va, type, offset);
        this.node = node;
    }

    PFProperty(PFProperty<S, B> source) throws BeanException {
        super(source);
        if (source.node instanceof Up2Format.Node<S, B> fn) {
            this.node = new Up2Mapper.Node<>(this.getType(), fn);
        } else {
            this.node = new Up2Format.Node<>((Up2Mapper.Node<S, B>) source.node);
        }
    }

    List<Property<?, B>> toList() {
        return node.toList();
    }

    @Override
    S defaultValue() {
        return null;
    }

}
