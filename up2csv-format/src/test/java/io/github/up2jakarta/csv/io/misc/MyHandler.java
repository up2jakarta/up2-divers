package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.core.hdl.FatalCollector;
import io.github.up2jakarta.csv.io.impl.GroupType;

public class MyHandler extends FatalCollector<MyRecord, GroupType, MyError> {

    public MyHandler(MyRecord row) {
        super(row, MyError::new);
    }

}
