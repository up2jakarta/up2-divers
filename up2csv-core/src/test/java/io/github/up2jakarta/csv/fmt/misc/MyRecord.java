package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.hdl.IRecordCollector;
import io.github.up2jakarta.csv.fmt.MiniRecord;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;

import java.util.LinkedList;
import java.util.List;

public class MyRecord extends MiniRecord<SegmentType> implements IRecordCollector<GroupType, SegmentType, MyError, MyRecord>, IFullRecord<SegmentType> {

    private final List<MyError> errors = new LinkedList<>();

    // UNIT Mode compatibility
    public MyRecord(SegmentType type, String[] data) {
        super(type, null, data);
    }

    public MyRecord(SegmentType type, String businessKey, String... data) {
        super(type, businessKey, data);
    }

    @Override
    // IRecordCollector compatibility
    public List<MyError> getErrors() {
        return errors;
    }

    @Override
    // FULL Mode compatibility
    public String getReference() {
        return null;
    }
}
