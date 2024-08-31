package io.github.up2jakarta.csv.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.io.Serializable;

@MappedSuperclass
public abstract class ImmutableEntity implements Serializable {

    @PreUpdate
    @PreRemove
    private void blockUD() {
        throw new UnsupportedOperationException("The entity [" + getClass().getSimpleName() + "] is immutable");
    }

}

