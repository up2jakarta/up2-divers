package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.hdl.IRecordEvent;
import io.github.up2jakarta.csv.fmt.MiniError;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.xml.api.PropertyException;

public class MyError extends MiniError<GroupType, MyRecord> implements IRecordEvent<GroupType, MyRecord, MyError> {

    public MyError(MyRecord row, int offset, GroupType type, PropertyException cause) {
        super(row, offset, type, cause);
    }
}
