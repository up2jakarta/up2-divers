package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.lov.core.AccessException;

import static io.github.up2jakarta.csv.hdl.FastHandler.of;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * Base reader for mono-segment format, that's able to read segments from input stream.
 *
 * @param <S> the segment type
 */
public abstract class Up2Reader<S extends Segment> {

    private final Up2Mapper<S, ?> mapper;

    protected Up2Reader(Up2Mapper<S, ?> mapper) {
        this.mapper = mapper;
    }

    public final S next() throws AccessException {
        final String[] record = this.record();
        if (record == null) return null;
        return mapper.map(of(ERROR), 0, record);
    }

    /**
     * Returns the next record in the stream.
     *
     * @return the next record, or else return <code>null</code>
     */
    protected abstract String[] record();

}
