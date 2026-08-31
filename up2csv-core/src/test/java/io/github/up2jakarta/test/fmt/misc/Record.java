package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.test.impl.SegmentType;

public class Record extends NeatRecord<SegmentType> {
    public Record(SegmentType type, String... data) {
        super(type, data);
    }
}
