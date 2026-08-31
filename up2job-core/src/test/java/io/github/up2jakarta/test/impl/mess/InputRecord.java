package io.github.up2jakarta.test.impl.mess;

import io.github.up2jakarta.csv.data.MessRecord;
import io.github.up2jakarta.test.impl.SegmentType;

public class InputRecord extends MessRecord<SegmentType> {

    public InputRecord(SegmentType type, String businessKey, String... data) {
        super(type, businessKey, data);
    }

}
