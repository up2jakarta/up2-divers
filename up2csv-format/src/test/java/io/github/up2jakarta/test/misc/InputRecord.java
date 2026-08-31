package io.github.up2jakarta.test.misc;

import io.github.up2jakarta.csv.data.FullRecord;
import io.github.up2jakarta.test.impl.SegmentType;

public class InputRecord extends FullRecord<SegmentType> {

    public InputRecord(String rowKey, SegmentType type, String invoiceNumber, String[] data) {
        super(rowKey, type, invoiceNumber, data);
    }

}
