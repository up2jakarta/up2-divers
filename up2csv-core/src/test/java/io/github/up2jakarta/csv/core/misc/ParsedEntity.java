package io.github.up2jakarta.csv.core.misc;

import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.impl.InputRecord;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.io.Serializable;

@MappedSuperclass
public abstract class ParsedEntity<K extends Serializable> implements Recordable<InputRecord> {

    @Transient
    private transient InputRecord row;

    public abstract K getKey();

    public abstract void setKey(K key);

    @Override
    public final InputRecord getRecord() {
        return row;
    }

    @Override
    public final void setRecord(InputRecord row) {
        this.row = row;
    }

}
