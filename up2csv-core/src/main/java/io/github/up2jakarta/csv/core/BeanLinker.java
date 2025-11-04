package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.fct.*;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Link accessor that's able to link beans of a relationship parent-child (association)
 *
 * @param <C> the child type
 * @param <P> the parent type
 */
public class BeanLinker<C extends Segment, P extends Segment> {

    public final Class<C> classType;
    public final Class<P> parentType;
    private final BiConsumer<P, C> setter;
    private final Function<P, Collection<C>> getter;

    /**
     * Constructor for collection relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    public BeanLinker(Class<P> parentType, Class<C> type, CollectionJoin<P, C> getter, BiConsumer<P, C> setter) {
        this.getter = requireNonNull(getter, "getter");
        this.setter = requireNonNull(setter, "setter");
        this.classType = requireNonNull(type, "class-type");
        this.parentType = requireNonNull(parentType, "parent-type");
    }

    /**
     * Constructor for one-to-one relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    public BeanLinker(Class<P> parentType, Class<C> type, SingleJoin<P, C> getter, BiConsumer<P, C> setter) {
        this(parentType, type, getter.many(), setter);
    }

    /**
     * Constructor for array relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    public BeanLinker(Class<P> parentType, Class<C> type, ArrayJoin<P, C> getter, BiConsumer<P, C> setter) {
        this(parentType, type, getter.values(), setter);
    }

    /**
     * Constructor for map-value relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    public BeanLinker(Class<P> parentType, Class<C> type, MapValueJoin<P, C> getter, BiConsumer<P, C> setter) {
        this(parentType, type, getter.values(), setter);
    }

    /**
     * Constructor for map-key relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    public BeanLinker(Class<P> parentType, Class<C> type, MapKeyJoin<P, C> getter, BiConsumer<P, C> setter) {
        this(parentType, type, getter.keys(), setter);
    }

    /**
     * Gets and returns the children memberships from the given parent.
     *
     * @param parent the parent bean
     * @return the list of children
     */
    final Collection<C> from(P parent) {
        return getter.apply(parent);
    }

    /**
     * Links the given beans in parent-child relationship logic.
     *
     * @param parent the parent bean
     * @param child  the child bean
     */
    final void link(P parent, C child) {
        setter.accept(parent, child);
    }

}
