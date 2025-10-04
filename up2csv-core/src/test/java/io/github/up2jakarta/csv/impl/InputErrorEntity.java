package io.github.up2jakarta.csv.impl;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_INPUT2_ERRORS")
@SuppressWarnings("unused")
public class InputErrorEntity extends AbstractError<InputErrorEntity.PKey> {

    @EmbeddedId
    private PKey key;

    @Transient
    private BusinessType type;

    @Override
    public PKey getKey() {
        return key;
    }

    @Override
    public void setKey(PKey key) {
        this.key = key;
    }

    @Override
    public BusinessType getType() {
        return type;
    }

    @Override
    public void setType(BusinessType type) {
        this.type = type;
    }

    @Embeddable
    public static class PKey implements Serializable, Key<InputRowEntity> {

        @ManyToOne(optional = false)
        @JoinColumns(
                value = {
                        @JoinColumn(name = "ERR_FILE_ID", referencedColumnName = "ROW_FILE_ID"),
                        @JoinColumn(name = "ERR_ROW_ORDER", referencedColumnName = "ROW_ORDER")
                },
                foreignKey = @ForeignKey(name = "FK_INPUT_ERROR2_ROW")
        )
        private InputRowEntity record;

        @Column(name = "ERR_ORDER", nullable = false)
        private Integer order;

        public InputRowEntity getRecord() {
            return record;
        }

        @Override
        public void setRecord(InputRowEntity record) {
            this.record = record;
        }

        public Integer getOrder() {
            return order;
        }

        @Override
        public void setOrder(Integer order) {
            this.order = order;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final InputErrorEntity.PKey that = (InputErrorEntity.PKey) other;
            return Objects.equals(record, that.record) && Objects.equals(order, that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(record, order);
        }

    }

}
