package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.SegmentWriter;

import java.io.IOException;

/**
 * {@link ModeType#FAST} Processor that able to do both segregation and aggregation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 * @see io.github.up2jakarta.csv.impl.SimpleAggregator
 */
public abstract class FastAggregator<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IError<B>> extends BusinessAggregator<T, B, I, R, E> {

    public FastAggregator(MapperFactory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FAST, type, rootNode, nodes);
    }

    /**
     * Segregates the given business-object to many records and notifies the callback for each one.
     *
     * @param bean     the business object to segregate
     * @param callback the segment listener
     * @throws BeanException for any problem when getting fields from business-object
     * @throws IOException   for some reason cannot be opened for writing by the callback.
     * @see FastSeparator#format(BusinessObject, SegmentWriter)
     */
    public final void format(T bean, SegmentWriter callback) throws BeanException, IOException {
        this.format(bean, () -> null, callback);
    }

}
