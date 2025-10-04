package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.codelist.CodeList;

/**
 * Contact marker for segment type definition.
 */
public interface IType<B extends DataType<B>, T extends IType<B, T>> extends CodeList<T> {

    /**
     * @return the segment class-type
     */
    default Class<? extends Segment> getClassType() {
        return this.joiner().getClassType();
    }

    /**
     * @return the segment business-type
     */
    B getBusinessType();

    /**
     * @return the error code for cardinality checking.
     */
    String getErrorCode();

    /**
     * Gets and returns the getter accessor of current type.
     *
     * @param <C> the child type
     * @param <P> the parent type
     * @return non-null accessor
     */
    <C extends Segment, P extends Segment> BeanJoiner<C, P> joiner();

    /**
     * Check if the given segment-type is a membership.
     *
     * @param type the child type
     * @return <code>true</code> if the given type is a valid child
     */
    default boolean holds(T type) {
        return type != this && type.joiner().getParentType() == this.joiner().getClassType();
    }

}
