package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.fmt.FullError;
import io.github.up2jakarta.lov.IError;

public class InputError extends FullError<GroupType, String, InputRecord> {

    public InputError(InputRecord row, int order, GroupType type, Integer offset, IError cause, String trace) {
        super(row, order, type, offset, cause, trace);
    }

}
