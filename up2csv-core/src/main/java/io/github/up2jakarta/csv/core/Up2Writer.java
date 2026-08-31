package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.lov.core.AccessException;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.Collection;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Base writer for mono-segment format, that's able to write segments to output stream.
 *
 * @param <S> the segment type
 * @param <D> the business term type
 */
public abstract class Up2Writer<S extends Segment, D extends ITerm<D>> implements Closeable, Flushable {

    private final Up2Flatter<S, D> mapper;

    protected Up2Writer(Up2Flatter<S, D> mapper) {
        this.mapper = notNull(mapper, this.getClass(), "mapper");
    }

    public final void header() throws IOException {
        this.write(mapper.header());
    }

    /**
     * Unmaps the given segment-objects to flat-data and writes them.
     *
     * @param beans the segment objects to write in mono-segment format
     * @throws IOException     for some reason cannot be opened for writing.
     * @throws AccessException for any problem when getting properties from the specified segments
     */
    public final void write(Collection<S> beans) throws IOException, AccessException {
        for (final S bean : beans) {
            final String[] data = mapper.unmap(bean, 0);
            this.write(data);
        }
        this.flush();
    }

    /**
     * Unmaps the given segment-object to flat-data and writes it.
     *
     * @param bean the segment object to write in mono-segment format
     * @throws IOException     for some reason cannot be opened for writing.
     * @throws AccessException for any problem when getting properties from the specified segments
     */
    public final void write(S bean) throws IOException, AccessException {
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
