package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;

public final class SimpleRecord<B extends DataType<B>, T extends IType<B, T>> implements IRecord<T> {

    private final T type;
    private final String[] data;

    public SimpleRecord(T type, String... data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public String[] getColumns() {
        return data;
    }
}
