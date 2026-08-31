package io.github.up2jakarta.test.impl.neat;

import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.test.impl.SegmentType;

public class InputRecord extends NeatRecord<SegmentType> {

    public InputRecord(SegmentType type, String... data) {
        super(type, data);
    }

}
