package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.Segment;

import java.util.Collection;
import java.util.List;

/**
 * Link accessor that's able to link beans of parent-child relationship (association)
 *
 * @param <C> the child type
 * @param <P> the parent type
 * @see Linker
 */
public interface ILinker<P extends Segment, C extends Segment> {

    /**
     * Constant holding the positive infinity.
     */
    int N = Integer.MAX_VALUE;

    /**
     * Creates and returns list of the specified <code>element</code> if not <code>null</code>, or else empty list.
     *
     * @param element the list element
     * @return the appropriate list
     */
    default List<C> of(C element) {
        if (element != null) {
            return List.of(element);
        }
        return List.of();
    }

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
