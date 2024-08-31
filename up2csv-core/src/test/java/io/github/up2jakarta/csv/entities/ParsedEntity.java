package io.github.up2jakarta.csv.entities;

import io.github.up2jakarta.csv.entities.input.InputRowEntity;
import io.github.up2jakarta.csv.extension.Parsed;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class ParsedEntity<K extends Serializable> extends ImmutableEntity implements Parsed<InputRowEntity> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private InputRowEntity row;

    public abstract K getKey();

    public abstract void setKey(K key);

    //@Override
    public final InputRowEntity getRow() {
        return row;
    }

    @Override
    public final void setRow(InputRowEntity row) {
        this.row = row;
    }

}
