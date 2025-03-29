package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.input.InputError.Key;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_INPUT1_ERRORS")
@SuppressWarnings("unused")
public class SimpleErrorEntity extends AbstractError<SimpleErrorEntity> implements Key<InputRowEntity> {

    @Id
    @ManyToOne(optional = false)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "FK_INPUT_ERROR1_ROW"),
            value = {
                    @JoinColumn(name = "ERR_FILE_ID", referencedColumnName = "ROW_FILE_ID"),
                    @JoinColumn(name = "ERR_ROW_ORDER", referencedColumnName = "ROW_ORDER")
            }
    )
    private InputRowEntity record;

    @Id
    @Column(name = "ERR_ORDER", nullable = false)
    private Integer order;

    @Transient
    private DataId type;

    @Override
    public SimpleErrorEntity getKey() {
        return this;
    }

    public InputRowEntity getRecord() {
        return record;
    }

    @Override
    public void setRecord(InputRowEntity row) {
        this.record = row;
    }

    public Integer getOrder() {
        return order;
    }

    @Override
    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public DataId getType() {
        return type;
    }

    @Override
    public void setType(DataId type) {
        this.type = type;
    }

}
