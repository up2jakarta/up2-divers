package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.SeverityType;

/**
 * Contact interface for segment type definition, used to segregate business-objects to multiple segments in one-shot.
 *
 * @param <B> the business data type
 * @param <I> self-type implementation
 * @see io.github.up2jakarta.csv.core.BusinessExporter
 * @see io.github.up2jakarta.csv.core.BusinessWriter
 */
public interface IType<B extends DataType<B>, I extends IType<B, I>> extends CodeList<I>, ILinker<Segment, Segment> {

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
     * @return the default data type
     */
    B getDataType();

    /**
     * @return the event code for cardinality checking or parent-child relationship linking.
     */
    String getEventCode();

    /**
     * @return the event level for cardinality checking or parent-child relationship linking.
     */
    SeverityType getEventLevel();

    /**
     * @return the segment class-type
     */
    Class<Segment> getClassType();

    /**
     * @return the parent class-type
     */
    Class<Segment> getParentType();

}
