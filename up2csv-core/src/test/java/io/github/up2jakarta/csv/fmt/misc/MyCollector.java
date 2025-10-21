package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.fmt.hdl.FastCollector;
import io.github.up2jakarta.csv.impl.GroupType;

public class MyCollector extends FastCollector<MyRecord, GroupType, MyError> {

    public MyCollector(MyRecord row) {
        super(row, MyError::new);
    }

}
