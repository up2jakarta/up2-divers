package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.input.InputError;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class InputRowErrorKey implements Serializable, InputError.Key<InputRowEntity> {

    @ManyToOne(optional = false)
    @JoinColumns(
            value = {
                    @JoinColumn(name = "ERR_LOAD_ID", referencedColumnName = "ROW_LOAD_ID"),
                    @JoinColumn(name = "ERR_ROW_ORDER", referencedColumnName = "ROW_ORDER")
            },
            foreignKey = @ForeignKey(name = "FK_INPUT_ERROR_ROW")
    )
    private InputRowEntity row;

    @Column(name = "ERR_ORDER", nullable = false)
    private Integer order;

    public InputRowEntity getRow() {
        return row;
    }

    @Override
    public void setRow(InputRowEntity row) {
        this.row = row;
    }

    public Integer getOrder() {
        return order;
    }

    @Override
    public void setOrder(Integer order) {
        this.order = order;
    }

}
