package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.data.Recordable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class ParsedEntity<K extends Serializable> implements Recordable<SegmentType, InputRowEntity> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private InputRowEntity row;

    public abstract K getKey();

    public abstract void setKey(K key);

    @Override
    public final InputRowEntity getRecord() {
        return row;
    }

    @Override
    public final void setRecord(InputRowEntity row) {
        this.row = row;
    }

}
