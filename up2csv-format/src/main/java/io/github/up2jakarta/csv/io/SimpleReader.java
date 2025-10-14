package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.impl.SimpleAggregator;
import io.github.up2jakarta.csv.impl.SimpleError;
import io.github.up2jakarta.csv.impl.SimpleRecord;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link io.github.up2jakarta.csv.ops.ModeType#FAST} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleAggregator
 */
public class SimpleReader<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>> extends FastFileReader<T, B, I, SimpleRecord<B, I>, SimpleError<B, I>> {

    public SimpleReader(SimpleAggregator<T, B, I> aggregator, CSVFormat format, String... nullValues) {
        super(aggregator, format, nullValues);
    }

    @Override
    protected final SimpleRecord<B, I> create(I type, String beanId, String[] data) {
        return new SimpleRecord<>(type, beanId, data);
    }

}
