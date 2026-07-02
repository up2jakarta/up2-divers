package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.ITerm;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.util.Objects;

/**
 * Base CSV file {@link ModeType#FAST} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class FastFileReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>, R extends IFastRecord<I>, E extends IEvent<B>> extends AbstractReader<T, B, I, R, E> {

    protected FastFileReader(FastImporter<B, I, T, R, E> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    final R transform(CSVRecord source) {
        final String[] values = source.values();
        final String bid = values[1];
        return importer.transform((t, d) -> this.create(t, bid, d), values);
    }

    @Override
    protected final boolean next(R pivot, R record) {
        return Objects.equals(pivot.getPivot(), record.getPivot());
    }

    protected abstract R create(I type, String beanId, String[] data);

}
