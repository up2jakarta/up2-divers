package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.job.csv.impl.SegmentType;

import java.io.File;

public class InputRecord extends io.github.up2jakarta.csv.fmt.hdl.InputRecord<SegmentType, File> {

    public InputRecord(File source, long lineId, String rowKey, SegmentType type, String invoiceKey, String[] data) {
        super(source, lineId, rowKey, type, invoiceKey, data);
    }

}
