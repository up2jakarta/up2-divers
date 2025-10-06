package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.BusinessObject;

import java.util.function.Supplier;

public enum ModeType {

    /**
     * Exclude record key.
     */
    FAST(0),
    /**
     * Include record key
     */
    FULL(1);

    final int typeIdIndex;
    final int beanIdIndex;
    final int length;

    ModeType(int offset) {
        this.typeIdIndex = offset++;
        this.beanIdIndex = offset++;
        this.length = offset;
    }

    public int getTypeIdIndex() {
        return typeIdIndex;
    }

    public int getBeanIdIndex() {
        return beanIdIndex;
    }

    <S extends BusinessObject> BusinessType<S> build(Class<S> type) {
        if (typeIdIndex == 0) {
            return new BusinessType<>(this, type, this::fast);
        }
        return new BusinessType<>(this, type, this::full);
    }

    private void full(String[] target, Supplier<String> rowId, IType<?, ?> type, BusinessObject source) {
        target[0] = rowId.get();
        target[beanIdIndex] = source.getReference();
        target[typeIdIndex] = type.getCode();
    }

    private void fast(String[] target, Supplier<String> rowId, IType<?, ?> type, BusinessObject source) {
        target[beanIdIndex] = source.getReference();
        target[typeIdIndex] = type.getCode();
    }

}
