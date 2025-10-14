package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;

import static io.github.up2jakarta.xml.adapters.KeyCoder.token;

public final class SimpleRecord<B extends DataType<B>, T extends IType<B, T>> implements IRecord<T> {

    private final T type;
    private final String[] data;
    private final String objectId;

    public SimpleRecord(T type, String objectId, String... data) {
        this.objectId = token(objectId);
        this.type = type;
        this.data = data;
    }

    @Override
    public String getBusinessReference() {
        return objectId;
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
