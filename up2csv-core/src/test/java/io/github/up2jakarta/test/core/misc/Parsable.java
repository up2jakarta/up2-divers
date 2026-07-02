package io.github.up2jakarta.test.core.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.test.impl.SegmentType;
import jakarta.persistence.Transient;

public abstract class Parsable implements Recordable<IRecord<SegmentType>> {

    @Transient
    private transient IRecord<SegmentType> origin;

    @Override
    public final IRecord<SegmentType> getRecord() {
        return origin;
    }

    @Override
    public final void setRecord(IRecord<SegmentType> origin) {
        this.origin = origin;
    }

}
