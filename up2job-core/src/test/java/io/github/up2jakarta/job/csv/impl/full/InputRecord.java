package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.fmt.hdl.PathRecord;
import io.github.up2jakarta.csv.fmt.hdl.PathSource;
import io.github.up2jakarta.job.csv.impl.SegmentType;

public class InputRecord extends PathRecord<SegmentType> {

    public InputRecord(PathSource source, long lineId, String rowKey, SegmentType type, String invoiceKey, String[] data) {
        super(source, lineId, rowKey, type, invoiceKey, data);
    }

}
