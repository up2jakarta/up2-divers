package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.impl.SimpleResult;

/**
 * Base business reader implementation for multi-segments format that's able to read business-objects.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class BusinessReader<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IError<B>> extends CSVReader<R> {

    protected final int beanIdIndex;
    protected final int typeIdIndex;
    protected final BusinessProcessor<B, I, T>.BusinessTyping typing;
    private final BusinessAggregator<T, B, I, R, E> aggregator;

    protected BusinessReader(BusinessAggregator<T, B, I, R, E> aggregator) {
        this.beanIdIndex = aggregator.mode.beanIdIndex;
        this.typeIdIndex = aggregator.mode.typeIdIndex;
        this.typing = aggregator.typing;
        this.aggregator = aggregator;
    }

    /**
     * Parses, validates and aggregates the next business-object.
     *
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code> argument
     * @throws BeanException for any problem when setting fields from input record
     */
    @SuppressWarnings("unused")
    public final <C> C read(BusinessCreator<C, T, E> creator) throws BeanException {
        return aggregator.parse(super.next(), creator);
    }

    /**
     * Parses, validates and aggregates the next business-object.
     *
     * @return the business-object with collected errors
     * @throws BeanException for any problem when setting fields from input record
     */
    public final SimpleResult<T, E> read() throws BeanException {
        return aggregator.parse(super.next());
    }

}
