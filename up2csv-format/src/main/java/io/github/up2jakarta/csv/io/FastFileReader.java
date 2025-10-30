package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.FastImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Base CSV file {@link ModeType#FAST} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class FastFileReader<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IEvent<B>> extends BaseFileReader<T, B, I, R, E> {

    protected FastFileReader(FastImporter<T, B, I, R, E> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    final R create(CSVRecord source, I type, String[] data) {
        return this.create(type, source.get(1), data);
    }

    @Override
    protected final boolean next(String beanId, R record) {
        return beanId.equals(record.getBusinessReference());
    }

    protected abstract R create(I type, String beanId, String[] data);

}
