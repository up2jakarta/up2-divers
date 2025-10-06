package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IErrorCause;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.clv.PropertyException;

public final class SimpleError<B extends DataType<B>, T extends IType<B, T>> implements IErrorCause<SimpleRecord<B, T>, B> {

    private final PropertyException cause;
    private final SimpleRecord<B, T> record;
    private final Integer offset;
    private final B type;

    public SimpleError(SimpleRecord<B, T> row, int offset, B type, PropertyException cause) {
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
    public SimpleRecord<B, T> getRecord() {
        return record;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public B getType() {
        return type;
    }

}
