package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.data.Recordable;

public abstract class Parsable implements Recordable<SegmentType, InputRowEntity> {

    private InputRowEntity origin;

    @Override
    public final InputRowEntity getRecord() {
        return origin;
    }

    @Override
    public final void setRecord(InputRowEntity origin) {
        this.origin = origin;
    }

}
