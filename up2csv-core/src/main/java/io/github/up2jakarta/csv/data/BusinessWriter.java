package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.BusinessExporter;
import io.github.up2jakarta.lov.core.AccessException;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Up2J Base business writer for multi-segments format, that's able to write business-objects to output stream.
 *
 * @param <T> the business object type
 */
public abstract class BusinessWriter<T extends Segment> implements Closeable, Flushable {
    private final BusinessExporter<?, ?, T> exporter;

    protected BusinessWriter(BusinessExporter<?, ?, T> exporter) {
        this.exporter = notNull(exporter, this.getClass(), "exporter");
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
        exporter.format(bean, this::write);
        this.flush();
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
