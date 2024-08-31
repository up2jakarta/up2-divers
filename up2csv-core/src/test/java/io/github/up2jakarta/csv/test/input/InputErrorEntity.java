package io.github.up2jakarta.csv.test.input;

import io.github.up2jakarta.csv.input.InputError;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_INPUT2_ERRORS")
@SuppressWarnings("unused")
public class InputErrorEntity extends AbstractError implements InputError<InputRowEntity, InputErrorEntity.PKey> {

    @EmbeddedId
    private PKey key;

    @Override
    public PKey getKey() {
        return key;
    }

    @Override
    public void setKey(PKey key) {
        this.key = key;
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

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final InputErrorEntity.PKey that = (InputErrorEntity.PKey) other;
            return Objects.equals(getRow(), that.getRow()) && Objects.equals(order, that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRow(), order);
        }

    }

}
