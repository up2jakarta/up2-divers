package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.fmt.FullError;
import io.github.up2jakarta.xml.api.IError;

public class InputError extends FullError<GroupType, InputRecord> {

    public InputError(InputRecord row, int order, GroupType type, int offset, IError cause, String trace) {
        super(row, order, type, offset, cause, trace);
    }

}
