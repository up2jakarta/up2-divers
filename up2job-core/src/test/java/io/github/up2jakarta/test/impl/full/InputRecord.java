package io.github.up2jakarta.test.impl.full;

import io.github.up2jakarta.csv.fmt.FullRecord;
import io.github.up2jakarta.test.impl.SegmentType;

public class InputRecord extends FullRecord<SegmentType, String> {

    public InputRecord(String rowKey, SegmentType type, String invoiceNumber, String[] data) {
        super(rowKey, type, invoiceNumber, data);
    }

}
