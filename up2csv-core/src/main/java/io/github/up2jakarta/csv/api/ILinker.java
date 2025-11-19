package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Segment;

import java.util.Collection;

/**
 * Link accessor that's able to link beans of a relationship parent-child (association)
 *
 * @param <C> the child type
 * @param <P> the parent type
 */
public interface ILinker<C extends Segment, P extends Segment> {

    /**
     * Gets and returns the children memberships from the given parent.
     *
     * @param parent the parent bean
     * @return the list of children
     */
    Collection<C> from(P parent);

    /**
     * Links the given beans in parent-child relationship logic.
     *
     * @param parent the parent bean
     * @param child  the child bean
     */
    void link(P parent, C child);

}
