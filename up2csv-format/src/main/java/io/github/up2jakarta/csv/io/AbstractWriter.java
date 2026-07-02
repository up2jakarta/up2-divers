package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Base CSV file {@link ModeType#FAST} writer implementation.
 *
 * @param <T> the business object type
 */
public abstract class AbstractWriter<T extends Segment> extends BusinessWriter<T> {

    private final CSVFormat format;

    private CSVPrinter printer;
    private Writer writer;

    AbstractWriter(FullExporter<?, ?, T> exporter, Supplier<String> generator, CSVFormat format) {
        super(exporter, generator);
        this.format = format;
    }

    AbstractWriter(FastExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter);
        this.format = format;
    }

    AbstractWriter(UnitExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter);
        this.format = format;
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

    @Override
    protected final void write(String[] record) throws IOException {
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
