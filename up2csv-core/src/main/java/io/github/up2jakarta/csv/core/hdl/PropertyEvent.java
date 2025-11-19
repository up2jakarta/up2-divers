package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.PropertyException;

/**
 * Simple implementation of input event based on cause exceptions, it's compatible with all modes.
 *
 * @param <B> the business data type
 * @param <R> the input record type
 */
public class PropertyEvent<B extends DataType<B>, R extends IRecord<?>> extends Event<B> implements IPropertyEvent<B, R> {

    private final PropertyException cause;
    private final R record;

    public PropertyEvent(R source, Integer offset, B type, PropertyException cause) {
        super(type, offset);
        this.record = source;
        this.cause = cause;
    }

    @Override
    public PropertyException getCause() {
        return cause;
    }

    @Override
    public R getRecord() {
        return record;
    }

    @Override
    public final String toString() {
        return this.getFormattedMessage();
    }

}
