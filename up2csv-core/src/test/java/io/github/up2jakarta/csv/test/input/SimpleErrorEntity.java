package io.github.up2jakarta.csv.test.input;

import io.github.up2jakarta.csv.input.InputError;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_INPUT1_ERRORS")
@SuppressWarnings("unused")
public class SimpleErrorEntity extends AbstractError implements InputError<InputRowEntity, SimpleErrorEntity>, InputError.Key<InputRowEntity> {

    @Id
    @ManyToOne(optional = false)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "FK_INPUT_ERROR1_ROW"),
            value = {
                    @JoinColumn(name = "ERR_FILE_ID", referencedColumnName = "ROW_FILE_ID"),
                    @JoinColumn(name = "ERR_ROW_ORDER", referencedColumnName = "ROW_ORDER")
            }
    )
    private InputRowEntity row;

    @Id
    @Column(name = "ERR_ORDER", nullable = false)
    private Integer order;

    @Override
    public SimpleErrorEntity getKey() {
        return this;
    }

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
