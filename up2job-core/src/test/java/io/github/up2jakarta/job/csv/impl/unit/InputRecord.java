package io.github.up2jakarta.job.csv.impl.unit;

import io.github.up2jakarta.csv.fmt.hdl.MiniRecord;
import io.github.up2jakarta.job.csv.impl.SegmentType;

public class InputRecord extends MiniRecord<SegmentType> {

    public InputRecord(SegmentType type, String... data) {
        super(type, null, data);
    }

}
