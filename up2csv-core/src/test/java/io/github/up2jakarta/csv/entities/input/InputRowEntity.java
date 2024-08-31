package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.converters.SegmentConverter;
import io.github.up2jakarta.csv.entities.ImmutableEntity;
import io.github.up2jakarta.csv.entities.Status;
import io.github.up2jakarta.csv.input.InputRow;
import io.github.up2jakarta.csv.test.input.SegmentType;
import jakarta.persistence.*;
import org.hibernate.annotations.Array;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "TB_INPUT_ROWS")
public class InputRowEntity extends ImmutableEntity implements InputRow {

    @OneToMany(mappedBy = "key.row")
    private final List<InputRowErrorEntity> errors = new LinkedList<>();
    @EmbeddedId
    private InputRowKey key;
    @Column(name = "ROW_STATUS", length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "ROW_FACT_NUM", length = 20, nullable = false)
    private String invoiceNumber;
    @Column(name = "ROW_NUM", nullable = false)
    private Integer number;
    @Column(name = "ROW_SEGMENT_CODE", length = 2, nullable = false)
    @Convert(converter = SegmentConverter.class)
    private SegmentType type;
    @Array(length = 16)
    @Column(name = "ROW_COLUMNS", length = 1024, nullable = false)
    private String[] columns;

    public InputRowKey getKey() {
        return key;
    }

    public void setKey(InputRowKey key) {
        this.key = key;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public SegmentType getType() {
        return type;
    }

    public void setType(SegmentType type) {
        this.type = type;
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public List<InputRowErrorEntity> getErrors() {
        return errors;
    }

}
