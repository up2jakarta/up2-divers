package io.github.up2jakarta.test.core.misc;

import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.test.impl.InputRecord;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class ParsedEntity<K extends Comparable<K>> implements Recordable<InputRecord> {

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
