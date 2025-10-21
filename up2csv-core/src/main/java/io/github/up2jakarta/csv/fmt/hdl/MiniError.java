package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.hdl.IErrorCause;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.clv.PropertyException;

/**
 * Simple implementation of error for {@link io.github.up2jakarta.csv.core.ModeType#FAST}.
 *
 * @param <B> the business data type
 * @param <R> the record type
 */
public class MiniError<B extends DataType<B>, R extends MiniRecord<?>> implements IErrorCause<R, B> {

    private final PropertyException cause;
    private final R record;
    private final int offset;
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
    public R getRecord() {
        return record;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public B getType() {
        return type;
    }

}
