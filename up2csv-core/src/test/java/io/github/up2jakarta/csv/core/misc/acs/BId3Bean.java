package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.data.Referencable;

public final class BId3Bean implements Referencable<Integer> {

    public Integer id;

    @Override
    public Integer getReference() {
        return id;
    }

    public void setReference(Integer value) {
        this.id = value;
    }
}