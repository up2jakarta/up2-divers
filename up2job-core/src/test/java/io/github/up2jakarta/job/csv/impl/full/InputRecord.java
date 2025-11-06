package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.fmt.FullRecord;
import io.github.up2jakarta.job.csv.impl.SegmentType;

public class InputRecord extends FullRecord<SegmentType> {

    public InputRecord(String rowKey, SegmentType type, String invoiceKey, String[] data) {
        super(rowKey, type, invoiceKey, data);
    }

}
