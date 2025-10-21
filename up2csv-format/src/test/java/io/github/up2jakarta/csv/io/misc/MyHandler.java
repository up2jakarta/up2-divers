package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.fmt.hdl.FastCollector;
import io.github.up2jakarta.csv.io.impl.GroupType;

public class MyHandler extends FastCollector<MyRecord, GroupType, MyError> {

    public MyHandler(MyRecord row) {
        super(row, MyError::new);
    }

}
