package io.github.up2jakarta.csv.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Base CSV reader for multi-segments format.
 *
 * @param <R> the input record type
 */
public abstract class Up2Aggregator<R extends Segment> implements Iterator<List<R>> {

    private R current;

    /**
     * Initializes the reader.
     */
    protected final void init() {
        current = this.record();
    }

    @Override
    public final List<R> next() {
        if (current == null) {
            return null;
        }
        final List<R> records = new LinkedList<>();
        final R pivot = current;
        do {
            records.add(current);
            current = this.record();
        } while (current != null && this.next(pivot, current));
        return records;
    }

    @Override
    public final boolean hasNext() {
        return current != null;
    }

    /**
     * Returns <code>true</code> when the specified record is a part of the bean segments.
     *
     * @param pivot  the first record
     * @param record the current record
     * @return <code>true</code> to continue the aggregation, else break it.
     */
    protected abstract boolean next(R pivot, R record);

    /**
     * Returns the next record in the stream.
     *
     * @return the next record, or else return <code>null</code>
     */
    protected abstract R record();

}
