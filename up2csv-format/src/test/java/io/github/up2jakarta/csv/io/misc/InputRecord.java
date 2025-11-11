package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.fmt.FullRecord;
import io.github.up2jakarta.csv.io.impl.SegmentType;

public class InputRecord extends FullRecord<SegmentType, String> {

    public InputRecord(String rowKey, SegmentType type, String invoiceNumber, String[] data) {
        super(rowKey, type, invoiceNumber, data);
    }

}
