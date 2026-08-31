package io.github.up2jakarta.csv.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.lov.TypeException;

/**
 * Simple implementation of input event based on cause exceptions, it's compatible with all modes.
 *
 * @param <B> the business term type
 * @param <R> the input record type
 */
public class PropertyEvent<B extends ITerm<B>, R extends IRecord<?>> extends Event<B> implements IPropertyEvent<B, R> {

    private final TypeException cause;
    private final R record;

    public PropertyEvent(R source, Integer offset, B type, TypeException cause) {
        super(type, offset);
        this.record = source;
        this.cause = cause;
    }

    @Override
    public TypeException getCause() {
        return cause;
    }

    @Override
    public R getRecord() {
        return record;
    }

    @Override
    public final String toString() {
        return this.getLocalizedMessage();
    }

}
