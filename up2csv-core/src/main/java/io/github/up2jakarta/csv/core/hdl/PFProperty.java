package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.core.BSNode;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

/**
 * Internal {@link io.github.up2jakarta.csv.cfg.Fragment} implementation.
 */
public final class PFProperty<S extends Segment, B extends DataType<B>> extends Property<S, B> {

    public final BSNode<S, B> node;

    public PFProperty(BSNode<S, B> node, PAccessor<?, S> va, B type, int offset, Fragment fragment) throws BeanException {
        super(va, type, offset, fragment, null);
        this.node = node;
    }

    public PFProperty(BSNode<S, B> node, PFProperty<S, B> source) throws BeanException {
        super(source, null);
        this.node = node;
    }

}
