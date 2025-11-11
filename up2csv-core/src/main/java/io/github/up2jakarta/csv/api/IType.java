package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.core.BeanLinker;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeList;

/**
 * Contact interface for segment type definition, used to segregate business-objects to multiple segments in one-shot.
 *
 * @param <B> the input data type
 * @param <I> self-type implementation
 * @see io.github.up2jakarta.csv.core.BusinessExporter
 * @see io.github.up2jakarta.csv.core.BusinessWriter
 */
public interface IType<B extends DataType<B>, I extends IType<B, I>> extends CodeList<I> {

    /**
     * @return the segment business-type
     */
    B getBusinessType();

    /**
     * @return the error code for cardinality checking or parent-child relationship linking.
     */
    String getErrorCode();

    /**
     * @return the severity level for cardinality checking or parent-child relationship linking.
     */
    SeverityType getErrorLevel();

    /**
     * Check if the given segment-type is a membership.
     *
     * @param type the child type
     * @return <code>true</code> if the given type is a valid child
     */
    default boolean holds(I type) {
        return type != this && type.getParentType() == this.getClassType();
    }

    /**
     * @return the segment class-type
     */
    @SuppressWarnings("unchecked")
    default <C extends Segment> Class<C> getClassType() {
        return (Class<C>) this.getJoinLinker().classType;
    }

    /**
     * @return the segment class-type
     */
    @SuppressWarnings("unchecked")
    default <C extends Segment> Class<C> getParentType() {
        return (Class<C>) this.getJoinLinker().parentType;
    }

    /**
     * Gets and returns the bean linker of current type.
     *
     * @param <C> the child type
     * @param <P> the parent type
     * @return non-null accessor
     */
    <C extends Segment, P extends Segment> BeanLinker<C, P> getJoinLinker();

}
