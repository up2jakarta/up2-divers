package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.BusinessObject;

import java.util.function.Supplier;

/**
 * Multi-segment format mode for meta-data columns.
 */
public enum ModeType {

    /**
     * Excludes record key.
     */
    FAST(0),
    /**
     * Includes record key
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

    public int getLength() {
        return length;
    }

    void full(String[] target, Supplier<String> rowId, IType<?, ?> type, BusinessObject source) {
        target[0] = rowId.get();
        target[beanIdIndex] = source.getReference();
        target[typeIdIndex] = type.getCode();
    }

    void fast(String[] target, Supplier<String> ignore, IType<?, ?> type, BusinessObject source) {
        target[beanIdIndex] = source.getReference();
        target[typeIdIndex] = type.getCode();
    }

}
