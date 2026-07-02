package io.github.up2jakarta.csv.core;

/**
 * Multi-segment format mode for meta-data columns.
 */
public enum ModeType {

    /**
     * Includes only segment-type. this mode avoids writing the business-reference for each segment.
     *
     * @see io.github.up2jakarta.csv.api.IRecord
     * @see UnitExporter
     * @see UnitImporter
     */
    UNIT(0, 1, 1),

    /**
     * Includes segment-type and business-reference but excludes record-reference.
     *
     * @see io.github.up2jakarta.csv.api.IFastRecord
     * @see FastExporter
     * @see FastImporter
     */
    FAST(0, 1, 2),
    /**
     * Includes all meta-data aka segment-type, business-reference and record-reference
     *
     * @see io.github.up2jakarta.csv.api.IFullRecord
     * @see FullExporter
     * @see FullImporter
     */
    FULL(1, 2, 3);

    final int typeIdIndex;
    final int beanIdIndex;
    final int length;

    ModeType(int typeIdIndex, int beanIdIndex, int length) {
        this.typeIdIndex = typeIdIndex;
        this.beanIdIndex = beanIdIndex;
        this.length = length;
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
