package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.Segment;

import static io.github.up2jakarta.csv.core.FastHandler.of;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;

public abstract class Up2Reader<S extends Segment> {

    private final Up2Mapper<S, ?> mapper;

    public Up2Reader(Up2Mapper<S, ?> mapper) {
        this.mapper = mapper;
    }

    public final S next() throws BeanException {
        final String[] record = this.record();
        if (record == null) {
            return null;
        }
        return mapper.map(of(ERROR), 0, record);
    }

    /**
     * Returns the next record in the stream.
     *
     * @return the next record, or else return <code>null</code>
     */
    protected abstract String[] record();

}
