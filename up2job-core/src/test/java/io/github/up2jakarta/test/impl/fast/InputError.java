package io.github.up2jakarta.test.impl.fast;

import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.test.impl.GroupType;

public class InputError extends PropertyEvent<GroupType, InputRecord> implements Segment {

    public InputError(InputRecord row, Integer offset, GroupType type, TypeException cause) {
        super(row, offset, type, cause);
    }

}
