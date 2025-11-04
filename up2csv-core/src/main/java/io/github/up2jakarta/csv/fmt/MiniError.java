package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.hdl.ICauseEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

/**
 * Simple implementation of input record based on errors, basically it's compatible with
 * {@link io.github.up2jakarta.csv.core.ModeType#FAST} and {@link io.github.up2jakarta.csv.core.ModeType#UNIT} modes.
 *
 * @param <B> the business data type
 * @param <R> the record type
 */
public class MiniError<B extends DataType<B>, R extends UnitRecord<?>> implements ICauseEvent<R, B> {

    private final PropertyException cause;
    private final int offset;
    private final R record;
    private final B type;

    public MiniError(R row, int offset, B type, PropertyException cause) {
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
