package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.Up2Reader;
import io.github.up2jakarta.csv.data.Segment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static io.github.up2jakarta.csv.prc.TrimProcessor.trim;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class SingleReader<S extends Segment> extends Up2Reader<S> implements Closeable {

    private final int minLength;
    private final CSVFormat format;
    private final String[] nullValues;

    private Iterator<CSVRecord> iterator;
    private FileReader reader;
    private CSVParser parser;

    public SingleReader(Up2Mapper<S, ?> mapper, CSVFormat format, String... nullValues) {
        this(mapper, format, 0, nullValues);
    }

    public SingleReader(Up2Mapper<S, ?> mapper, CSVFormat format, int minLength, String... nullValues) {
        super(mapper);
        this.format = format;
        this.minLength = minLength;
        this.nullValues = nullValues;
    }

    /**
     * Opens the given file argument and initializes the reader.
     *
     * @param file the file to open
     * @return the header
     * @throws IOException if the file does not exist or for some other reason cannot be opened for reading.
     */
    public String[] open(final File file) throws IOException {
        return this.open(new FileReader(file, UTF_8));
    }

    /**
     * Opens the given file-reader argument and initializes the reader.
     *
     * @param reader the file-reader to open
     * @return the header record
     * @throws IOException if the file does not exist or for some other reason cannot be opened for reading.
     */
    public String[] open(final FileReader reader) throws IOException {
        this.parser = format.parse(reader);
        this.iterator = parser.iterator();
        this.reader = reader;
        if (iterator.hasNext()) {
            return iterator.next().values(); // Ignore header
        }
        return null;
    }

    @Override
    protected String[] record() {
        String[] values;
        do {
            try {
                final CSVRecord record = this.iterator.next();
                values = trim(record.values(), nullValues);
            } catch (NoSuchElementException ex) {
                return null;
            }
        } while (values.length < minLength);
        return values;
    }

    @Override
    public void close() throws IOException {
        this.parser.close();
        this.reader.close();
    }

}
