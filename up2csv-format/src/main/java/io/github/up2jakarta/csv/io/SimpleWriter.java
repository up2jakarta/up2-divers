package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.impl.SimpleAggregator;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link io.github.up2jakarta.csv.ops.ModeType#FAST} reader implementation.
 *
 * @param <T> the business object type
 * @see SimpleAggregator
 */
public class SimpleWriter<T extends BusinessObject> extends FastFileWriter<T> {

    public SimpleWriter(SimpleAggregator<T, ?, ?> aggregator, CSVFormat format) {
        super(aggregator, format);
    }

}
