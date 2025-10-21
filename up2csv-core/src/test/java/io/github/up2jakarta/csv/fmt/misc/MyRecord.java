package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.fmt.hdl.MiniRecord;
import io.github.up2jakarta.csv.impl.SegmentType;

public class MyRecord extends MiniRecord<SegmentType> {

    public MyRecord(SegmentType type, String businessKey, String... data) {
        super(type, businessKey, data);
    }

}
