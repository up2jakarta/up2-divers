package io.github.up2jakarta.test.misc;

import io.github.up2jakarta.csv.core.hdl.BusinessEvent;
import io.github.up2jakarta.lov.IError;
import io.github.up2jakarta.test.impl.GroupType;

public class InputError extends BusinessEvent<GroupType, InputRecord> {

    public InputError(InputRecord row, int order, GroupType type, Integer offset, IError cause, String trace) {
        super(row, order, type, offset, cause, trace);
    }

}
