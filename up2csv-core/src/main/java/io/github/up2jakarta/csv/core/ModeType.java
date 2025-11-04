package io.github.up2jakarta.csv.core;

/**
 * Multi-segment format mode for meta-data columns.
 */
public enum ModeType {

    /**
     * Includes only segment-type, this mode is used only for serializing and deserializing objects.
     * It avoids writing the business-reference for each segment, it will be written only for root segment.
     */
    UNIT(-1, 0, 1, 1),

    /**
     * Includes segment-type and business-reference but excludes record-reference.
     */
    FAST(-1, 0, 1, 2),
    /**
     * Includes alla meta-dat aka segment-type and business-reference record-reference
     */
    FULL(0, 1, 2, 3);

    final int rowKeyIndex;
    final int typeIdIndex;
    final int beanIdIndex;
    final int length;

    ModeType(int rowKeyIndex, int typeIdIndex, int beanIdIndex, int length) {
        this.rowKeyIndex = rowKeyIndex;
        this.typeIdIndex = typeIdIndex;
        this.beanIdIndex = beanIdIndex;
        this.length = length;
    }

    public int getRowKeyIndex() {
        return rowKeyIndex;
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

}
