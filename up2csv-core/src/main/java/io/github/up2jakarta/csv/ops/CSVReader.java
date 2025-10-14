package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.data.Separable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Base CSV reader for multi-segments format.
 *
 * @param <R> the record type
 */
public abstract class CSVReader<R extends Separable> implements Iterator<List<R>> {

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
        final String bid = current.getBusinessReference();
        do {
            records.add(current);
            current = this.record();
        } while (current != null && bid.equals(current.getBusinessReference()));
        return records;
    }

    @Override
    public final boolean hasNext() {
        return current != null;
    }

    /**
     * Returns the next record in the stream.
     *
     * @return the next record, or else return <code>null</code>
     */
    protected abstract R record();

}
