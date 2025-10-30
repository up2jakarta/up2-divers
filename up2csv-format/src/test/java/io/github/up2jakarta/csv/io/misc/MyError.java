package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.fmt.hdl.MiniError;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.xml.api.PropertyException;

public class MyError extends MiniError<GroupType, MyRecord> {

    public MyError(MyRecord row, int offset, GroupType type, PropertyException cause) {
        super(row, offset, type, cause);
    }

}
