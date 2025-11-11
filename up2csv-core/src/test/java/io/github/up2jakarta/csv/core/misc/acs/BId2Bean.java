package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.data.BusinessObject;

public final class BId2Bean implements BusinessObject<Integer> {

    public Integer id;

    @Override
    public Integer getReference() {
        return id;
    }

    @Override
    public void setReference(Integer value) {
        this.id = value;
    }
}