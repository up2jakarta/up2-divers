package io.github.up2jakarta.csv.test.persistence;

import io.github.up2jakarta.csv.persistence.InputRow;
import jakarta.persistence.*;
import org.hibernate.annotations.Array;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_INPUT_ROWS")
@SuppressWarnings("unused")
public class InputRowEntity implements InputRow {

    @EmbeddedId
    private InputRowEntity.PKey key;

    @Column(name = "ROW_REFERENCE", length = 20, nullable = false)
    private String reference;

    @Column(name = "ROW_TYPE", length = 2, nullable = false)
    private SegmentType type;

    @Array(length = 16)
    @Column(name = "ROW_COLUMNS", length = 1024, nullable = false)
    private String[] columns;

    public PKey getKey() {
        return key;
    }

    public void setKey(PKey key) {
        this.key = key;
    }

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
    public static class PKey implements Serializable {

        @Column(name = "ROW_FILE_ID", nullable = false)
        private Long fileId;

        @Id
        @Column(name = "ROW_ORDER", nullable = false)
        private Integer order;

        public Long getFileId() {
            return fileId;
        }

        public void setFileId(Long fileId) {
            this.fileId = fileId;
        }

        public Integer getOrder() {
            return order;
        }

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
            final PKey that = (PKey) other;
            return Objects.equals(fileId, that.fileId) && Objects.equals(order, that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fileId, order);
        }
    }

}
