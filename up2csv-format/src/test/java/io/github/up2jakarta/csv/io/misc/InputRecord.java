package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.io.impl.SegmentType;

public class InputRecord extends io.github.up2jakarta.csv.fmt.InputRecord<SegmentType> {

    public InputRecord(String rowKey, SegmentType type, String invoiceKey, String[] data) {
        super(rowKey, type, invoiceKey, data);
    }

}
