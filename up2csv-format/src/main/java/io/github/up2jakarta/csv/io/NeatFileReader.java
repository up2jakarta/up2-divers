package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.NeatImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Base CSV file {@link ModeType#NEAT} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class NeatFileReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>, R extends IRecord<I>, E extends IEvent<B>> extends AbstractReader<T, B, I, R, E> {
    private final I main;

    protected NeatFileReader(NeatImporter<B, I, T, R, E> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
        this.main = importer.type();
    }

    @Override
    final R transform(CSVRecord source) {
        return importer.transform(this::create, source.values());
    }

    @Override
    protected final boolean next(R ignore, R record) {
        return record.getType() != main;
    }

    protected abstract R create(I type, String[] data);

}
