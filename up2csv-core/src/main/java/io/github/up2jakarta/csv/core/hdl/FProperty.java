package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.core.BSNode;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Optional;

import static io.github.up2jakarta.csv.core.hdl.FProperty.FOProperty;
import static io.github.up2jakarta.csv.core.hdl.FProperty.FWProperty;

/**
 * Internal {@link io.github.up2jakarta.csv.cfg.Fragment} implementation.
 */
public abstract sealed class FProperty<S extends Segment, V, B extends DataType<B>> extends Property<S, V, B> permits FOProperty, FWProperty {

    public final BSNode<S, B> node;

    private FProperty(BSNode<S, B> node, PAccessor<?, V> va, B type, int offset, Fragment pf, V dv) throws BeanException {
        super(va, type, offset, pf, dv);
        this.node = node;
    }

    private FProperty(BSNode<S, B> node, FProperty<S, V, B> source, V defaultValue) throws BeanException {
        super(source, defaultValue);
        this.node = node;
    }

    @Override
    protected final V parse(String value, int offset, EventHandler<?, B, ?> handler) throws BeanException {
        throw BeanException.of(this.getSource(), "unsupported operation");
    }

    @Override
    protected final String format(V value) throws BeanException {
        throw BeanException.of(this.getSource(), "unsupported operation");
    }

    /**
     * Internal representation for non {@link java.util.Optional} property.
     */
    public static final class FOProperty<S extends Segment, B extends DataType<B>> extends FProperty<S, S, B> {
        public FOProperty(BSNode<S, B> node, PAccessor<?, S> va, B type, int offset, Fragment pf) throws BeanException {
            super(node, va, type, offset, pf, null);
        }

        public FOProperty(BSNode<S, B> node, FProperty<S, S, B> source) throws BeanException {
            super(node, source, null);
        }

        @Override
        protected S wrap(S value) {
            return value;
        }

        @Override
        protected S from(S value) {
            return value;
        }
    }

    /**
     * Internal representation for {@link java.util.Optional} property.
     */
    public static final class FWProperty<S extends Segment, B extends DataType<B>> extends FProperty<S, Optional<S>, B> {
        public FWProperty(BSNode<S, B> n, PAccessor<?, Optional<S>> a, B pt, int po, Fragment pf) throws BeanException {
            super(n, a, pt, po, pf, Optional.empty());
        }

        public FWProperty(BSNode<S, B> node, FWProperty<S, B> source) throws BeanException {
            super(node, source, Optional.empty());
        }

        @Override
        protected Optional<S> wrap(S value) {
            return Optional.ofNullable(value);
        }

        @Override
        protected S from(Optional<S> value) {
            return value.orElse(null);
        }
    }

}
