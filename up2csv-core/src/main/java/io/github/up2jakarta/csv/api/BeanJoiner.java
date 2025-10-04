package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.api.fct.*;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Collection;
import java.util.function.Function;

/**
 * Getter accessor that's able to get memberships of a relationship parent-child (association).
 *
 * @param <T> the child type
 * @param <P> the parent type
 */
public abstract class BeanJoiner<T extends Segment, P extends Segment> {

    final Class<T> type;
    final Class<P> parentType;
    private final Function<P, Collection<T>> joins;

    /**
     * Constructor for collection relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     */
    protected BeanJoiner(Class<P> parentType, Class<T> type, IJoin<P, T> getter) {
        this.parentType = parentType;
        this.type = type;
        this.joins = getter;
    }

    /**
     * Constructor for on-to-one relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     */
    protected BeanJoiner(Class<P> parentType, Class<T> type, SingleJoin<P, T> getter) {
        this(parentType, type, getter.many());
    }

    /**
     * Constructor for array relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     */
    protected BeanJoiner(Class<P> parentType, Class<T> type, ArrayJoin<P, T> getter) {
        this(parentType, type, getter.values());
    }

    /**
     * Constructor for map-value relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     */
    protected BeanJoiner(Class<P> parentType, Class<T> type, MapValueJoin<P, T> getter) {
        this(parentType, type, getter.values());
    }

    /**
     * Constructor for map-key relationship.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     */
    protected BeanJoiner(Class<P> parentType, Class<T> type, MapKeyJoin<P, T> getter) {
        this(parentType, type, getter.keys());
    }

    /**
     * @return the child type
     */
    public final Class<? extends T> getClassType() {
        return type;
    }

    /**
     * @return the parent type
     */
    public final Class<? extends P> getParentType() {
        return parentType;
    }

    /**
     * Gets and returns the children memberships from the given parent.
     *
     * @param parent the parent bean
     * @return the list of children
     */
    public final Collection<T> joins(P parent) {
        return joins.apply(parent);
    }

}
