package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.hdl.ICauseEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

/**
 * Simple implementation of input event based on cause exceptions, it's compatible with all modes.
 *
 * @param <B> the business data type
 * @param <R> the record type
 */
public class ECause<B extends DataType<B>, R extends UnitRecord<?>> implements ICauseEvent<R, B> {

    private final PropertyException cause;
    private final int offset;
    private final R record;
    private final B type;

    public ECause(R row, int offset, B type, PropertyException cause) {
        this.offset = offset;
        this.cause = cause;
        this.record = row;
        this.type = type;
    }

    @Override
    public PropertyException getCause() {
        return cause;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public R getRecord() {
        return record;
    }

    @Override
    public B getType() {
        return type;
    }

    @Override
    public final String toString() {
        return this.getFormattedMessage();
    }

}
