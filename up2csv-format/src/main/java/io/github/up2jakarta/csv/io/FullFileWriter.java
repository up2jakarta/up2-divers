package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.ops.FullAggregator;
import io.github.up2jakarta.csv.ops.FullSeparator;
import io.github.up2jakarta.csv.ops.FullWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Base CSV file {@link io.github.up2jakarta.csv.ops.ModeType#FULL} writer implementation.
 *
 * @param <T> the business object type
 */
public class FullFileWriter<T extends BusinessObject> extends FullWriter<T> {

    private final CSVFormat format;

    private CSVPrinter printer;
    private FileWriter writer;

    protected FullFileWriter(FullAggregator<T, ?, ?, ?, ?> processor, CSVFormat format, Supplier<String> generator) {
        super(processor, generator);
        this.format = format;
    }

    @SuppressWarnings("unused")
    protected FullFileWriter(FullSeparator<T, ?, ?> processor, CSVFormat format, Supplier<String> generator) {
        super(processor, generator);
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
    protected void open(final FileWriter writer) throws IOException {
        this.printer = new CSVPrinter(writer, format);
        this.writer = writer;
        this.reset();
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
