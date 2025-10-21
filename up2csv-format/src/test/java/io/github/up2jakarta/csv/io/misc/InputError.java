package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.fmt.hdl.PathError;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.Optional;

public class InputError extends PathError<GroupType, InputRecord> {

    public InputError(InputRecord row, int order, GroupType dt, int di, SeverityType level, String code, String msg, Optional<String> trace) {
        super(row, order, dt, di, level, code, msg, trace);
    }

}
