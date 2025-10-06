package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.api.fct.*;
import io.github.up2jakarta.csv.data.Segment;

import java.util.function.BiConsumer;

/**
 * Link accessor that's able to link beans of a relationship parent-child (association)
 *
 * @param <C> the child type
 * @param <P> the parent type
 */
public abstract class BeanLinker<C extends Segment, P extends Segment> extends BeanJoiner<C, P> {

    private final BiConsumer<P, C> linker;

    /**
     * Constructor for collection relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    protected BeanLinker(Class<P> parentType, Class<C> type, IJoin<P, C> getter, BiConsumer<P, C> setter) {
        super(parentType, type, getter);
        this.linker = setter;
    }

    /**
     * Constructor for one-to-one relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    protected BeanLinker(Class<P> parentType, Class<C> type, SingleJoin<P, C> getter, BiConsumer<P, C> setter) {
        super(parentType, type, getter);
        this.linker = setter;
    }

    /**
     * Constructor for array relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    protected BeanLinker(Class<P> parentType, Class<C> type, ArrayJoin<P, C> getter, BiConsumer<P, C> setter) {
        super(parentType, type, getter);
        this.linker = setter;
    }

    /**
     * Constructor for map-value relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    protected BeanLinker(Class<P> parentType, Class<C> type, MapValueJoin<P, C> getter, BiConsumer<P, C> setter) {
        super(parentType, type, getter);
        this.linker = setter;
    }

    /**
     * Constructor for map-key relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    protected BeanLinker(Class<P> parentType, Class<C> type, MapKeyJoin<P, C> getter, BiConsumer<P, C> setter) {
        super(parentType, type, getter);
        this.linker = setter;
    }

    /**
     * Links the given beans in parent-child relationship logic.
     *
     * @param parent the parent bean
     * @param child  the child bean
     */
    public final void link(P parent, C child) {
        linker.accept(parent, child);
    }

}
