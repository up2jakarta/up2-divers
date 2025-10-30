package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

/**
 * Extension of {@link IType}, used to aggregate multiple segments to business-objects in one-shot.
 *
 * @param <B> the input data type
 * @param <I> the self-type implementation
 * @see io.github.up2jakarta.csv.core.BusinessImporter
 * @see io.github.up2jakarta.csv.core.BusinessReader
 */
public interface IFullType<B extends DataType<B>, I extends IFullType<B, I>> extends IType<B, I> {

    @Override
    @SuppressWarnings("unchecked")
    default BeanJoiner<?, ?> joiner() {
        return this.linker();
    }

    /**
     * Gets and returns the bean linker of current type.
     *
     * @param <C> the child type
     * @param <P> the parent type
     * @return non-null accessor
     */
    <C extends Segment, P extends Segment> BeanLinker<C, P> linker();

}
