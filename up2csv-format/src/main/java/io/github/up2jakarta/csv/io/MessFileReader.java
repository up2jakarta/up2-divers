package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IMessRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.ModeType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.util.Objects;

/**
 * Base CSV file {@link ModeType#MESS} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class MessFileReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>, R extends IMessRecord<I>, E extends IEvent<B>> extends AbstractReader<T, B, I, R, E> {

    protected MessFileReader(MessImporter<B, I, T, R, E> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
        assert importer.mode() == ModeType.MESS : "issue has been detected";
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
