package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.ops.FastAggregator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Base CSV file {@link io.github.up2jakarta.csv.ops.ModeType#FAST} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class FastFileReader<T extends BusinessObject, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IError<B>> extends BaseFileReader<T, B, I, R, E> {

    public FastFileReader(FastAggregator<T, B, I, R, E> aggregator, CSVFormat format, String... nullValues) {
        super(aggregator, format, nullValues);
    }

    /**
     * Opens the given file argument and initializes the reader.
     *
     * @param file the file to open
     * @throws IOException if the file does not exist or for some other reason cannot be opened for reading.
     */
    public void open(final File file) throws IOException {
        this.open(new FileReader(file, UTF_8));
    }

    /**
     * Opens the given file-reader argument and initializes the reader.
     *
     * @param reader the file-reader to open
     * @throws IOException if the file does not exist or for some other reason cannot be opened for reading.
     */
    public void open(final FileReader reader) throws IOException {
        super.open(reader);
    }

    @Override
    final R create(CSVRecord source, I type, String beanId, String[] data) {
        return this.create(type, beanId, data);
    }

    protected abstract R create(I type, String beanId, String[] data);

}
