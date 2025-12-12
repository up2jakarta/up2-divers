package io.github.up2jakarta.test.impl.full;

import io.github.up2jakarta.csv.fmt.FullError;
import io.github.up2jakarta.lov.IError;
import io.github.up2jakarta.test.impl.GroupType;

public class InputError extends FullError<GroupType, String, InputRecord> {

    public InputError(InputRecord row, int order, GroupType type, Integer offset, IError cause, String trace) {
        super(row, order, type, offset, cause, trace);
    }

}
