package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public abstract class Up2Writer<S extends Segment, D extends DataType<D>> implements Closeable, Flushable {

    private final Up2Format<S, D> mapper;

    public Up2Writer(Up2Format<S, D> mapper) {
        this.mapper = mapper;
    }

    public final void header() throws IOException {
        this.write(mapper.header());
    }

    /**
     * Segregates the given business-object to many records and writes them.
     *
     * @param bean the segment object to write in multi-segments format
     * @throws IOException   for some reason cannot be opened for writing.
     * @throws BeanException for any problem when getting fields from business-object
     */
    public final void write(S bean) throws IOException, BeanException {
        final String[] data = mapper.unmap(bean, 0);
        this.write(data);
    }

    /**
     * Writes the given record in the stream.
     *
     * @param record the record data
     * @throws IOException for some reason cannot be opened for writing.
     */
    protected abstract void write(String[] record) throws IOException;

}
