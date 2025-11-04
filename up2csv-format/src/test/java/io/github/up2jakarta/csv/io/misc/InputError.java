package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.xml.api.IError;

public class InputError extends io.github.up2jakarta.csv.fmt.InputError<GroupType, InputRecord> {

    public InputError(InputRecord row, int order, GroupType type, int offset, IError cause, String trace) {
        super(row, order, type, offset, cause, trace);
    }

}
