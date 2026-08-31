package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Writer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class SingleWriter<S extends Segment, D extends ITerm<D>> extends Up2Writer<S, D> {

    private final CSVFormat format;

    private CSVPrinter printer;
    private FileWriter writer;

    public SingleWriter(Up2Flatter<S, D> mapper, CSVFormat format) {
        super(mapper);
        this.format = notNull(format, this.getClass(), "format");
    }

    /**
     * Opens the given file argument and initializes the writer.
     *
     * @param file the file to write in
     * @throws IOException if the file does not exist or for some other reason cannot be opened for writing.
     */
    public void open(final File file) throws IOException {
        this.open(new FileWriter(file, UTF_8));
    }

    /**
     * Opens the given file-writer argument and initializes the writer.
     *
     * @param writer the file-writer to write in
     * @throws IOException if the file does not exist or for some other reason cannot be opened for writing.
     */
    public void open(final FileWriter writer) throws IOException {
        this.printer = new CSVPrinter(writer, format);
        this.writer = writer;
        super.header();
    }

    @Override
    protected void write(String[] record) throws IOException {
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
