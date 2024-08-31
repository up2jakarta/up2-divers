package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.entities.ErrorEntity;
import io.github.up2jakarta.csv.input.InputError;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_INPUT_ROW_ERRORS")
public class InputRowErrorEntity extends ErrorEntity implements InputError<InputRowEntity, InputRowErrorKey> {

    @EmbeddedId
    private InputRowErrorKey key;

    @Column(name = "ERR_COLUMN_INDEX", nullable = false)
    private Integer offset;

    @Override
    public InputRowErrorKey getKey() {
        return key;
    }

    @Override
    public void setKey(InputRowErrorKey key) {
        this.key = key;
    }

    public Integer getOffset() {
        return offset;
    }

    @Override
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
