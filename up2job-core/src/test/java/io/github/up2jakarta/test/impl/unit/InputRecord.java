package io.github.up2jakarta.test.impl.unit;

import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.test.impl.SegmentType;

public class InputRecord extends UnitRecord<SegmentType> {

    public InputRecord(SegmentType type, String... data) {
        super(type, data);
    }

}
