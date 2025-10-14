package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_INPUT_RECORDS")
@SuppressWarnings("unused")
public class InputRowEntity implements IRecordEntity<SegmentType, InputFileEntity, InputRowEntity.PKey> {

    @EmbeddedId
    private PKey key;

    @Column(name = "ROW_INVOICE_NUM", length = 20, nullable = false)
    private String invoiceNumber;

    @Column(name = "ROW_LINE_NUM", length = 16)
    private String reference;

    @Column(name = "ROW_TYPE", length = 2, nullable = false)
    private SegmentType type;

    @Column(name = "ROW_COLUMNS", length = 1024, nullable = false)
    private String[] columns;

    @Override
    public PKey getKey() {
        return key;
    }

    public void setKey(PKey key) {
        this.key = key;
    }

    @Override
    public String getBusinessReference() {
        return invoiceNumber;
    }

    public void setBusinessReference(String reference) {
        this.invoiceNumber = reference;
    }

    @Override
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final InputRowEntity that = (InputRowEntity) other;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Embeddable
    public static class PKey implements IKey<InputFileEntity>, Serializable {

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "ROW_FILE_ID", foreignKey = @ForeignKey(name = "FK_IN_RECORD_FILE"))
        private InputFileEntity source;

        @Column(name = "ROW_ORDER", nullable = false)
        private Long order;

        @Override
        public InputFileEntity getSource() {
            return source;
        }

        @Override
        public void setSource(InputFileEntity source) {
            this.source = source;
        }

        @Override
        public Long getRecordNumber() {
            return order;
        }

        @Override
        public void setRecordNumber(Long order) {
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
            final PKey that = (PKey) other;
            return Objects.equals(source, that.source) && Objects.equals(order, that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, order);
        }
    }

}
