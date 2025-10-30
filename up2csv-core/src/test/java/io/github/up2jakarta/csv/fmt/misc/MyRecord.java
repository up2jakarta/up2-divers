package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.hdl.IUnitRecord;
import io.github.up2jakarta.csv.fmt.hdl.MiniRecord;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;

import java.util.LinkedList;
import java.util.List;

public class MyRecord extends MiniRecord<SegmentType> implements IUnitRecord<GroupType, SegmentType, MyError, MyRecord> {

    private final List<MyError> errors = new LinkedList<>();

    public MyRecord(SegmentType type, String businessKey, String... data) {
        super(type, businessKey, data);
    }

    @Override
    public List<MyError> getErrors() {
        return errors;
    }
}
