package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.BusinessExporter;
import io.github.up2jakarta.csv.data.BusinessWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * CSV File Writer implementation that supports the out-of-the-box modes
 *
 * @param <T> the business object type
 */
public abstract class AbstractWriter<T extends Segment> extends BusinessWriter<T> {

    private final CSVFormat format;
    private final int index;
    private CSVPrinter printer;
    private Writer writer;

    protected AbstractWriter(BusinessExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter);
        this.index = exporter.mode().getIndex();
        this.format = notNull(format, this.getClass(), "format");
    }

    /**
     * Opens the given file argument and initializes the writer.
     *
     * @param file the file to write in
     * @throws IOException if the file does not exist or for some other reason cannot be opened for writing.
     */
    public final void open(final File file) throws IOException {
        this.open(new FileWriter(file, UTF_8));
    }

    /**
     * Opens the given file-writer argument and initializes the writer.
     *
     * @param writer the file-writer to write in
     * @throws IOException if the file does not exist or for some other reason cannot be opened for writing.
     */
    public final void open(final Writer writer) throws IOException {
        this.printer = new CSVPrinter(writer, format);
        this.writer = writer;
        this.reset();
    }

    @Override
    protected void init(T bean) throws IOException {
        printer.printComment("");
    }

    /**
     * Resets the buffer to the initial state.
     */
    protected abstract void reset();

    /**
     * Returns the extra-data added by the extended mode at the specified {@code index}.
     *
     * @param index the index of extra-data starting from {@code 0}
     */
    protected abstract String get(int index);

    @Override
    protected final void write(String[] record) throws IOException {
        if (index != 0) {
            for (var i = 0; i < index; i++) {
                record[i] = this.get(i);
            }
        }
        printer.printRecord((Object[]) record);
    }

    @Override
    public void flush() throws IOException {
        printer.flush();
    }

    @Override
    public void close() throws IOException {
        printer.close();
        writer.close();
    }
}
