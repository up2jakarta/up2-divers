package io.github.up2jakarta.job.csv.impl.unit;

import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.job.csv.impl.GroupType;
import io.github.up2jakarta.lov.PropertyException;

public class InputError extends PropertyEvent<GroupType, InputRecord> implements Segment {

    public InputError(InputRecord row, Integer offset, GroupType type, PropertyException cause) {
        super(row, offset, type, cause);
    }

}
