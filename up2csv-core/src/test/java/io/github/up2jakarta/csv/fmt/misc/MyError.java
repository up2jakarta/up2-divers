package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.fmt.hdl.MiniError;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.xml.clv.PropertyException;

public class MyError extends MiniError<GroupType, MyRecord> {

    public MyError(MyRecord row, int offset, GroupType type, PropertyException cause) {
        super(row, offset, type, cause);
    }

}
