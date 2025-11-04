package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.data.Referencable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Base CSV file {@link ModeType#FAST} writer implementation.
 *
 * @param <T> the business object type
 */
public abstract class BaseFileWriter<T extends Referencable> extends BusinessWriter<T> {

    private final CSVFormat format;

    private CSVPrinter printer;
    private FileWriter writer;

    BaseFileWriter(FullExporter<?, ?, T> exporter, Supplier<String> generator, CSVFormat format) {
        super(exporter, generator);
        this.format = format;
    }

    BaseFileWriter(FastExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter);
        this.format = format;
    }

    BaseFileWriter(UnitExporter<?, ?, T> exporter, CSVFormat format) {
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
        this.reset();
    }

    /**
     * Opens the given file-writer argument and initializes the writer.
     *
     * @param writer the file-writer to write in
     * @throws IOException if the file does not exist or for some other reason cannot be opened for writing.
     */
    public final void open(final FileWriter writer) throws IOException {
        this.printer = new CSVPrinter(writer, format);
        this.writer = writer;
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
