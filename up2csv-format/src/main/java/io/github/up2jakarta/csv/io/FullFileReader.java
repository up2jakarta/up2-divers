package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.hdl.IErrorEntity;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.api.hdl.ISourceEntity;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.ops.FullAggregator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Base CSV file {@link io.github.up2jakarta.csv.ops.ModeType#FULL} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <S> the source type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class FullFileReader<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, S extends ISourceEntity<?>, R extends IRecordEntity<I, S, ?>, E extends IErrorEntity<R, ?, B>> extends BaseFileReader<T, B, I, R, E> {

    private S resource;

    public FullFileReader(FullAggregator<T, B, I, R, E> aggregator, CSVFormat format, String... nullValues) {
        super(aggregator, format, nullValues);
    }

    /**
     * Opens the given file argument and initializes the reader.
     *
     * @param file     the file to open
     * @param resource the record resource {@link IRecordEntity.IKey#setSource(ISourceEntity)}
     * @throws IOException if the file does not exist or for some other reason cannot be opened for reading.
     */
    public void open(final File file, S resource) throws IOException {
        this.open(new FileReader(file, UTF_8), resource);
    }

    /**
     * Opens the given file-reader argument and initializes the reader.
     *
     * @param reader   the file-reader to open
     * @param resource the record resource {@link IRecordEntity.IKey#setSource(ISourceEntity)}
     * @throws IOException if the file does not exist or for some other reason cannot be opened for reading.
     */
    public void open(final FileReader reader, S resource) throws IOException {
        super.open(reader);
        this.resource = resource;
    }

    @Override
    final R create(CSVRecord source, I type, String beanId, String[] data) {
        final R row = this.create(source.values()[0], type, beanId, data);
        final IRecordEntity.IKey<S> key = row.getKey();
        if (key != null) {
            key.setRecordNumber(source.getRecordNumber());
            key.setSource(resource);
        }
        return row;
    }

    protected abstract R create(String recordId, I type, String beanId, String[] data);

}
