package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.core.hdl.FatalCollector;
import io.github.up2jakarta.csv.impl.GroupType;

public class MyCollector extends FatalCollector<MyRecord, GroupType, MyError> {

    public MyCollector(MyRecord row) {
        super(row, MyError::new);
    }

}
