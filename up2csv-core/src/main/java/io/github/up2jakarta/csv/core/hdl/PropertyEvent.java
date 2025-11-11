package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

/**
 * Simple implementation of input event based on cause exceptions, it's compatible with all modes.
 *
 * @param <B> the business data type
 * @param <R> the record type
 */
public class PropertyEvent<B extends DataType<B>, R extends IRecord<?>> implements IPropertyEvent<R, B> {

    private final PropertyException cause;
    private final Integer offset;
    private final R record;
    private final B type;

    public PropertyEvent(R row, Integer offset, B type, PropertyException cause) {
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
    public Integer getOffset() {
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
