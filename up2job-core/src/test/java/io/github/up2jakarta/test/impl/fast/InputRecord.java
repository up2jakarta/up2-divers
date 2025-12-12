package io.github.up2jakarta.test.impl.fast;

import io.github.up2jakarta.csv.fmt.FastRecord;
import io.github.up2jakarta.test.impl.SegmentType;

public class InputRecord extends FastRecord<SegmentType, String> {

    public InputRecord(SegmentType type, String businessKey, String... data) {
        super(type, businessKey, data);
    }

}
