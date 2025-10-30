package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.hdl.IUnitError;
import io.github.up2jakarta.csv.fmt.hdl.MiniError;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.xml.api.PropertyException;

public class MyError extends MiniError<GroupType, MyRecord> implements IUnitError<GroupType, MyRecord, MyError> {

    public MyError(MyRecord row, int offset, GroupType type, PropertyException cause) {
        super(row, offset, type, cause);
    }
}
