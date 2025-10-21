package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BusinessImporter;
import io.github.up2jakarta.csv.core.BusinessReader;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static io.github.up2jakarta.csv.prc.TrimProcessor.trim;

/**
 * Base CSV file reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class BaseFileReader<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IError<B>> extends BusinessReader<B, I, T, R, E> implements Closeable {

    private final CSVFormat format;
    private final String[] nullValues;

    private Iterator<CSVRecord> iterator;
    private FileReader reader;
    private CSVParser parser;

    BaseFileReader(BusinessImporter<B, I, T, R, E> importer, CSVFormat format, String... nullValues) {
        super(importer);
        this.format = format;
        this.nullValues = nullValues;
    }

    /**
     * Opens the given file-reader argument and initializes the reader.
     *
     * @param reader the file-reader to open
     * @throws IOException if the file does not exist or for some other reason cannot be opened for reading.
     */
    void open(final FileReader reader) throws IOException {
        this.parser = format.parse(reader);
        this.iterator = parser.iterator();
        this.reader = reader;
        this.init();
    }

    @Override
    protected final R record() {
        CSVRecord record;
        String[] values;
        do {
            try {
                record = this.iterator.next();
                values = record.values();
            } catch (NoSuchElementException ex) {
                return null;
            }
        } while (values.length <= beanIdIndex);
        trim(values, nullValues);
        final I type = typing.type(values);
        final String[] data = typing.truncate(type, values);
        return this.create(record, type, values[beanIdIndex], data);
    }

    abstract R create(CSVRecord source, I type, String beanId, String[] data);

    @Override
    public final void close() throws IOException {
        this.parser.close();
        this.reader.close();
    }

}
