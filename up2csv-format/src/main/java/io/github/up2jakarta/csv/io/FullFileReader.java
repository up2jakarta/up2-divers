package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.util.Objects;

/**
 * Base CSV file {@link ModeType#FULL} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the input data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class FullFileReader<T extends Segment, B extends DataType<B>, I extends IType<B, I>, R extends IFullRecord<I, ?>, E extends IEvent<B>> extends BaseFileReader<T, B, I, R, E> {

    protected FullFileReader(FullImporter<B, I, T, R, E> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    final R create(CSVRecord source, I type, String[] data) {
        final String[] values = source.values();
        return this.create(source.getRecordNumber(), values[0], type, values[2], data);
    }

    @Override
    protected final boolean next(R pivot, R record) {
        return Objects.equals(pivot.getPivot(), record.getPivot());
    }

    protected abstract R create(long lineId, String rowId, I type, String beanId, String[] data);

}
