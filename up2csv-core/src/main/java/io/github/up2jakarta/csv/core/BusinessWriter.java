package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.Resettable;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.AccessException;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * Base business writer for multi-segments format, that's able to write business-objects to output stream.
 *
 * @param <T> the business object type
 */
public abstract class BusinessWriter<T extends Segment> implements Closeable, Flushable {

    private final Supplier<String> generator;
    private final BusinessExporter<?, ?, T> exporter;

    protected BusinessWriter(FullExporter<?, ?, T> exporter, Supplier<String> generator) {
        this.generator = generator;
        this.exporter = exporter;
    }

    protected BusinessWriter(FastExporter<?, ?, T> exporter) {
        this.exporter = exporter;
        this.generator = () -> null;
    }

    protected BusinessWriter(UnitExporter<?, ?, T> exporter) {
        this.exporter = exporter;
        this.generator = () -> null;
    }

    /**
     * Segregates the given business-object to many records and writes them.
     *
     * @param bean the business object to write in multi-segments format
     * @throws IOException     for some reason cannot be opened for writing.
     * @throws AccessException for any problem when getting properties from the specified business-object
     */
    public final void write(T bean) throws IOException, AccessException {
        this.init(bean);
        exporter.format(bean, generator, this::write);
        this.flush();
    }

    /**
     * Resets the record-reference generator if implements {@link Resettable},
     * useful for reusing the writer for many streams.
     */
    protected final void reset() {
        if (generator instanceof Resettable r) {
            r.reset();
        }
    }

    /**
     * Callback before starting format of business-object and write its multiple segments,
     * useful for bean validation, full-filling missed data or checking the output stream.
     *
     * @param bean the business object to write in multi-segments format
     */
    protected abstract void init(T bean) throws IOException;

    /**
     * Writes the given record in the stream.
     *
     * @param record the record data
     * @throws IOException for some reason cannot be opened for writing.
     */
    protected abstract void write(String[] record) throws IOException;

}
