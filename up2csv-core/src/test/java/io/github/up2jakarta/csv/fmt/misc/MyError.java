package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.hdl.ISelfEvent;
import io.github.up2jakarta.csv.fmt.ECause;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.xml.api.PropertyException;

public class MyError extends ECause<GroupType, MyRecord> implements ISelfEvent<GroupType, MyRecord, MyError> {

    public MyError(MyRecord row, int offset, GroupType type, PropertyException cause) {
        super(row, offset, type, cause);
    }
}
